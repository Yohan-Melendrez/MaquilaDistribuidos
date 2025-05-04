package service.inspeccion.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import service.inspeccion.dtos.AsignarLoteDTO;
import service.inspeccion.dtos.InspectorDTO;
import service.inspeccion.dtos.NotificacionDTO;
import service.inspeccion.dtos.ProductoDelLoteDTO;
import service.inspeccion.dtos.RegistroInspeccionDTO;
import service.inspeccion.modelo.*;
import service.inspeccion.rabbit.ProductorNotificaciones;
import service.inspeccion.repositorio.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspeccionService {

    @Autowired
    private InspeccionRepositorio inspeccionRepo;
    @Autowired
    private LoteRepositorio loteRepo;
    @Autowired
    private LoteInspectorRepositorio loteInspectorRepo;
    @Autowired
    private ProductoRepositorio productoRepo;
    @Autowired
    private ErrorRepositorio errorRepo;
    @Autowired
    private LoteProductoRepositorio loteProductoRepo;
    @Autowired
    private ProductorNotificaciones productorNotificaciones;
    @Autowired
    private InspectorRepositorio inspectorRepo;
    private final RestTemplate restTemplate;
    @Value("${qa.service.url:http://localhost:8081/qa}")
    private String qaServiceUrl;

    public InspeccionService() {
       
        this.restTemplate = new RestTemplate();
    }

    public void registrarInspeccion(RegistroInspeccionDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Producto producto = productoRepo.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inspector inspector = inspectorRepo.findByNombre(dto.getInspector())
                .orElseThrow(() -> new RuntimeException("Inspector no encontrado"));

        // --- Guardar inspecciones y determinar nivel ---
        NivelAtencion nivelMasAlto = NivelAtencion.BAJO;
        for (Integer idError : dto.getErroresSeleccionados()) {
            ErrorProduccion err = errorRepo.findById(idError)
                    .orElseThrow(() -> new RuntimeException("Error no encontrado"));
            Inspeccion ins = new Inspeccion();
            ins.setLote(lote);
            ins.setProducto(producto);
            ins.setError(err);
            ins.setInspector(inspector);
            inspeccionRepo.save(ins);
            if (err.getNivelAtencion().ordinal() > nivelMasAlto.ordinal()) {
                nivelMasAlto = err.getNivelAtencion();
            }
        }

        // --- Actualizar estado del lote ---
        lote.setEstado("Rechazado");
        lote.setNivelAtencion(nivelMasAlto);
        loteRepo.save(lote);

        // --- Crear DTO de notificación ---
        NotificacionDTO noti = new NotificacionDTO();
        noti.setTitulo("Lote rechazado");
        noti.setMensaje(
                "El lote " + lote.getNombreLote() +
                        " fue rechazado por " + dto.getInspector() +
                        ". Nivel: " + nivelMasAlto);
        noti.setTipo("DEFECTO");
        noti.setFechaEnvio(LocalDateTime.now());
        noti.setIdInspector(inspector.getIdInspector());
        noti.setNombreInspector(inspector.getNombre());

        // --- 3) Enviar notificación a RabbitMQ ---
        productorNotificaciones.enviarNotificacion(noti);

        // --- 4) Enviar notificación a QA para guardarla en su sistema ---
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<NotificacionDTO> req = new HttpEntity<>(noti, headers);
            restTemplate.postForEntity(qaServiceUrl + "/externa", req, String.class);
        } catch (Exception e) {
            // Loguea el error pero no detengas el flujo principal
            System.err.println("Error al notificar a QA: " + e.getMessage());
        }

        // --- 5) Desactivar el inspector que acaba de reportar ---
        inspector.setActivo(false);
        inspectorRepo.save(inspector);
    }

    public void asignarLoteAInspector(AsignarLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        boolean existe = loteInspectorRepo
                .existsByLote_IdLoteAndInspector(dto.getIdLote(), dto.getInspector());
        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este lote ya fue asignado a " + dto.getInspector());
        }

        LoteInspector asignacion = new LoteInspector();
        asignacion.setLote(lote);
        asignacion.setInspector(dto.getInspector());
        loteInspectorRepo.save(asignacion);
    }

    public List<Lote> obtenerLotesPorInspector(String inspector) {
        return loteInspectorRepo.findByInspector(inspector).stream()
                .map(LoteInspector::getLote)
                .collect(Collectors.toList());
    }

    public List<ProductoDelLoteDTO> obtenerProductosDeLote(Integer idLote) {
        return loteProductoRepo.findByLote_IdLote(idLote).stream()
                .map(lp -> new ProductoDelLoteDTO(
                        lp.getProducto().getIdProducto(),
                        lp.getProducto().getNombre(),
                        lp.getProducto().getDescripcion(),
                        lp.getCantidad()))
                .collect(Collectors.toList());
    }

    public List<ErrorProduccion> obtenerTodosLosErrores() {
        return errorRepo.findAll();
    }

    public List<ErrorProduccion> obtenerErroresPorProducto(Integer idProducto) {
        return errorRepo.findErroresPorProducto(idProducto);
    }

    public List<InspectorDTO> obtenerTodosLosInspectores() {
        return inspectorRepo.findAll().stream()
                .filter(Inspector::getActivo)
                .map(i -> new InspectorDTO(i.getIdInspector(), i.getNombre()))
                .collect(Collectors.toList());
    }
}
