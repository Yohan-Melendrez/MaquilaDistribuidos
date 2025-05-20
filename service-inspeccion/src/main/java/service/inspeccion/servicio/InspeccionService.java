package service.inspeccion.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import service.inspeccion.dtos.AsignarLoteDTO;
import service.inspeccion.dtos.ErrorDTO;
import service.inspeccion.dtos.InspectorDTO;
import service.inspeccion.dtos.NotificacionDTO;
import service.inspeccion.dtos.ProductoDelLoteDTO;
import service.inspeccion.dtos.RegistroInspeccionDTO;
import service.inspeccion.modelo.ErrorProduccion;
import service.inspeccion.modelo.Inspeccion;
import service.inspeccion.modelo.Inspector;
import service.inspeccion.modelo.Lote;
import service.inspeccion.modelo.NivelAtencion;
import service.inspeccion.modelo.Producto;
import service.inspeccion.repositorio.ErrorRepositorio;
import service.inspeccion.repositorio.InspeccionRepositorio;
import service.inspeccion.repositorio.InspectorRepositorio;
import service.inspeccion.repositorio.LoteProductoRepositorio;
import service.inspeccion.repositorio.LoteRepositorio;
import service.inspeccion.repositorio.ProductoRepositorio;

@Service
public class InspeccionService {

    @Autowired
    private InspeccionRepositorio inspeccionRepo;
    @Autowired
    private LoteRepositorio loteRepo;
    @Autowired
    private ProductoRepositorio productoRepo;
    @Autowired
    private ErrorRepositorio errorRepo;
    @Autowired
    private LoteProductoRepositorio loteProductoRepo;
    @Autowired
    private InspectorRepositorio inspectorRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${qa.service.url:http://servicio-qa:8082/qa}")
    private String qaServiceUrl;

    /**
     * 1) Registra la inspección (persistencia local). 2) Envía notificación a
     * Rabbit y luego a QA para registrar la notificación. 3) Desactiva el
     * inspector localmente.
     */
    public void registrarInspeccion(RegistroInspeccionDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Producto producto = productoRepo.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inspector inspector = inspectorRepo.findByNombre(dto.getInspector())
                .orElseThrow(() -> new RuntimeException("Inspector no encontrado"));

        // --- Guardar cada inspección y calcular nivel de atención máximo ---
        NivelAtencion nivelMasAlto = NivelAtencion.BAJO;
        for (Integer idError : dto.getErroresSeleccionados()) {
            ErrorProduccion err = errorRepo.findById(idError)
                    .orElseThrow(() -> new RuntimeException("Error no encontrado"));
            Inspeccion ins = new Inspeccion();
            ins.setLote(lote);
            ins.setProducto(producto);
            ins.setError(err);
            ins.setInspector(inspector);
            ins.setDetalle_error(dto.getDetalleError());

            inspeccionRepo.save(ins);
//            if (err.getNivelAtencion().ordinal() > nivelMasAlto.ordinal()) {
//                nivelMasAlto = err.getNivelAtencion();
//            }
        }

        // --- Actualizar estado del lote ---
        lote.setEstado("Rechazado");
        lote.setNivelAtencion(nivelMasAlto);
        loteRepo.save(lote);

        // --- Preparar DTO de notificación ---
        NotificacionDTO noti = new NotificacionDTO();
        noti.setTitulo("Lote rechazado");
        noti.setMensaje("El lote " + lote.getNombreLote()
                + " fue rechazado por " + dto.getInspector()
                + ". Nivel: " + nivelMasAlto);
        noti.setTipo("DEFECTO");
        noti.setFechaEnvio(LocalDateTime.now());
        noti.setIdInspector(inspector.getIdInspector());
        noti.setNombreInspector(inspector.getNombre());

        // --- 1) RabbitMQ local ---
//        productorNotificaciones.enviarNotificacion(noti);
        // --- 2) POST a QA para que registre la notificación ---
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<NotificacionDTO> req = new HttpEntity<>(noti, headers);
            restTemplate.postForEntity(qaServiceUrl + "/notificaciones", req, String.class);
        } catch (Exception e) {
            System.err.println("Error al notificar a QA: " + e.getMessage());
        }

        // --- 3) Desactivar el inspector local ---
        inspector.setActivo(false);
        inspectorRepo.save(inspector);
    }

    /**
     * En lugar de persistir localmente, delega la asignación a QA.
     */
    public void asignarLoteAInspector(AsignarLoteDTO dto) {
        // Validaciones mínimas
        if (dto.getIdLote() == null || dto.getInspector() == null) {
            throw new IllegalArgumentException("El ID del lote o del inspector no puede ser null");
        }

        // Llamada REST a QA
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AsignarLoteDTO> req = new HttpEntity<>(dto, headers);
            restTemplate.postForEntity(qaServiceUrl + "/asignarLote", req, String.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("QA respondió con error: " + e.getResponseBodyAsString(), e);
        }
    }

    /**
     * Trae los lotes asignados al inspector consultando a QA.
     * @return 
     */
    public List<Lote> obtenerLotesPorInspector(Integer idInspector) {
        List<Lote> asignados = loteRepo.findByInspector_IdInspector(idInspector);

        return asignados.stream()
                .filter(lote -> {
                    List<Producto> productosDelLote = loteProductoRepo.findByLote_IdLote(lote.getIdLote())
                            .stream()
                            .map(lp -> lp.getProducto())
                            .distinct()
                            .toList();

                    List<Inspeccion> inspecciones = inspeccionRepo.findByLote_IdLoteAndInspector_IdInspector(
                            lote.getIdLote(), idInspector
                    );

                    long productosInspeccionados = inspecciones.stream()
                            .map(i -> i.getProducto().getIdProducto())
                            .distinct()
                            .count();

                    return productosInspeccionados < productosDelLote.size(); // aún faltan por inspeccionar
                })
                .collect(Collectors.toList());
    }

    public List<ProductoDelLoteDTO> obtenerProductosDeLote(Integer idLote) {
        return loteProductoRepo.findByLote_IdLote(idLote)
                .stream()
                .map(lp -> new ProductoDelLoteDTO(
                lp.getProducto().getIdProducto(),
                lp.getProducto().getNombre(),
                lp.getProducto().getDescripcion(),
                lp.getCantidad()
        ))
                .collect(Collectors.toList());
    }

    public List<ErrorProduccion> obtenerTodosLosErrores() {
        return errorRepo.findAll();
    }

    public List<ErrorDTO> obtenerErroresPorProducto(Integer idProducto) {
        List<ErrorProduccion> errores = errorRepo.findErroresPorProducto(idProducto);
        return errores.stream()
                .map(ErrorDTO::new)
                .collect(Collectors.toList());
    }

    public List<InspectorDTO> obtenerTodosLosInspectores() {
        return inspectorRepo.findAll().stream()
                .map(i -> new InspectorDTO(i.getIdInspector(), i.getNombre()))
                .collect(Collectors.toList());
    }
}
