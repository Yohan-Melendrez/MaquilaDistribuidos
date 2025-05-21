package service.qa.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.ErrorDTO;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.dto.LotesDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.dto.ProductoDTO;
import service.qa.modelo.ErrorProduccion;
import service.qa.modelo.Inspector;
import service.qa.modelo.Lote;
import service.qa.modelo.LoteInspector;
import service.qa.modelo.LoteProducto;
import service.qa.modelo.Notificacion;
import service.qa.modelo.Producto;
import service.qa.rabbit.ProductorNotificaciones;
import service.qa.repositorio.ErrorRepositorio;
import service.qa.repositorio.InspectorRepositorio;
import service.qa.repositorio.LoteInspectorRepositorio;
import service.qa.repositorio.LoteRepositorio;
import service.qa.repositorio.NotificacionRepositorio;
import service.qa.repositorio.ProductoRepositorio;

@Service
@Transactional
public class ServicioQA {

    @Autowired
    private ErrorRepositorio errorRepo;
    @Autowired
    private ProductoRepositorio productoRepo;
    private final LoteRepositorio loteRepo;
    private final InspectorRepositorio inspectorRepo;
    private final NotificacionRepositorio notificacionRepo;
    private final RestTemplate restTemplate;
    private final List<NotificacionDTO> notificacionesEnMemoria = new ArrayList<>();
    private final List<NotificacionDTO> notificacionesERP = new ArrayList<>();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Value("${erp.integration.enabled:false}")
    private boolean erpIntegrationEnabled;

    @Value("${erp.service.url:http://localhost:8082/erp}")
    private String erpServiceUrl;

    @Autowired
    private LoteInspectorRepositorio loteInspectorRepo;

    public ServicioQA(
            LoteRepositorio loteRepo,
            InspectorRepositorio inspectorRepo,
            NotificacionRepositorio notificacionRepo,
            @Lazy ProductorNotificaciones productor) {
        this.loteRepo = loteRepo;
        this.inspectorRepo = inspectorRepo;
        this.notificacionRepo = notificacionRepo;
        this.restTemplate = new RestTemplate();
    }

    public void recibirNotificacionDefecto(Lote lote) {
        loteRepo.save(lote);
    }

    public void asignarNivelAtencion(EvaluacionDefectoDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote()).orElse(null);
        if (lote != null) {
            lote.setNivelAtencion(dto.getNivelAtencion());
            loteRepo.save(lote);
            if (erpIntegrationEnabled) {
                enviarActualizacionAERP(dto);
            }
        }
    }

    private void enviarActualizacionAERP(EvaluacionDefectoDTO dto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EvaluacionDefectoDTO> request = new HttpEntity<>(dto, headers);
            restTemplate.postForEntity(erpServiceUrl + "/actualizarNivelAtencion", request, String.class);
        } catch (Exception e) {
            System.err.println("Error al enviar actualización al ERP: " + e.getMessage());
        }
    }

    public String asignarLoteAInspector(AsignacionLoteDTO dto) {
        if (dto.getIdLote() == null || dto.getIdInspector() == null) {
            throw new IllegalArgumentException("El ID del lote o del inspector no puede ser null");
        }

        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("No se encontró el lote " + dto.getIdLote()));

        // ✅ Validar si ya tiene inspector asignado
        if (lote.getInspector() != null) {
            return "YA_ASIGNADO";
        }

        Inspector insp = inspectorRepo.findById(dto.getIdInspector())
                .orElseThrow(() -> new RuntimeException("No se encontró el inspector " + dto.getIdInspector()));

        LoteInspector li = new LoteInspector();
        li.setLote(lote);
        li.setInspector(insp.getNombre());
        loteInspectorRepo.save(li);

        lote.setInspector(insp);
        loteRepo.save(lote);

        insp.setActivo(false);
        inspectorRepo.save(insp);

        return "ASIGNACION_EXITOSA";
    }

    public void Notificaciones(NotificacionDTO dto) {
        notificacionesEnMemoria.add(dto);
        messagingTemplate.convertAndSend("/topic/notificaciones", dto);
    }

    public List<LotesDTO> obtenerTodosLosLotes() {
        return loteRepo.findAll().stream()
                .filter(lote -> "En proceso".equalsIgnoreCase(lote.getEstado()))
                .map(LotesDTO::new)
                .collect(Collectors.toList());
    }

    public List<LotesDTO> obtenerLotesConErrores() {
        return loteRepo.findAll().stream()
                .filter(l -> l.getProductos().stream()
                        .anyMatch(lp -> !lp.getProducto().getErrores().isEmpty()))
                .map(LotesDTO::new)
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNotificaciones() {
        return notificacionesEnMemoria;
    }

    public List<Inspector> obtenerInspectoresActivos() {
        return inspectorRepo.findAll().stream()
                .filter(Inspector::getActivo)
                .collect(Collectors.toList());
    }

    public LotesDTO obtenerLotePorId(Integer id) {
        return loteRepo.findById(id)
                .map(LotesDTO::new)
                .orElse(null);
    }

    public void recibirLoteDesdeERP(LotesDTO dto) {
        Lote lote = new Lote();
        lote.setNombreLote(dto.getNombreLote());
        lote.setEstado(dto.getEstado());

        List<LoteProducto> productosDelLote = new ArrayList<>();

        for (ProductoDTO productoDTO : dto.getProductos()) {
            Producto producto = productoRepo.findByNombre(productoDTO.getNombre()).orElse(null);
            if (producto == null) {
                producto = new Producto();
                producto.setNombre(productoDTO.getNombre());
                producto.setDescripcion("");
                producto = productoRepo.save(producto);
            }

            List<ErrorProduccion> erroresProducto = new ArrayList<>();
            for (ErrorDTO errorDTO : productoDTO.getErrores()) {
                ErrorProduccion error = errorRepo.findByDescripcionAndNombre(errorDTO.getDescripcion(),
                        errorDTO.getNombre());
                if (error == null) {
                    error = new ErrorProduccion();
                    error.setDescripcion(errorDTO.getDescripcion());
                    error.setNombre(errorDTO.getNombre());
                    error.setCostoUsd(errorDTO.getCosto());
                    error.setNivelAtencion(null);
                    error = errorRepo.save(error);
                }

                if (!producto.getErrores().contains(error)) {
                    producto.getErrores().add(error);
                }
                if (!error.getProductos().contains(producto)) {
                    error.getProductos().add(producto);
                }

                erroresProducto.add(error);
            }

            producto.setErrores(erroresProducto);
            productoRepo.save(producto);

            LoteProducto lp = new LoteProducto();
            lp.setLote(lote);
            lp.setProducto(producto);
            lp.setCantidad(productoDTO.getCantidad());
            productosDelLote.add(lp);
        }

        lote.setProductos(productosDelLote);
        lote = loteRepo.save(lote); // IMPORTANTE: persistimos para obtener el ID

        // Enviar notificación STOMP
        NotificacionDTO noti = new NotificacionDTO();
        noti.setTitulo("Envio de lote de ERP");
        noti.setTipo("Lote Nuevo");
        noti.setMensaje("Nuevo lote recibido: " + lote.getNombreLote());
        noti.setFechaEnvio(java.time.LocalDateTime.now()); 

        Notificaciones(noti); // Método ya existente que guarda en memoria y envía STOMP
    }

    public void notificarLlegadaAErp(Integer idLote) {
        Lote lote = loteRepo.findById(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        NotificacionDTO noti = new NotificacionDTO();
        noti.setTitulo("Llegada de lote");
        noti.setTipo("INFORMACION");
        noti.setMensaje("El lote " + lote.getNombreLote() + " ha sido registrado en QA");
        noti.setFechaEnvio(java.time.LocalDateTime.now());

        // Guardar solo si quieres mostrar desde backend luego
        notificacionesERP.add(noti);

        // Enviar a los suscriptores de ERP (WebSocket)
        messagingTemplate.convertAndSend("/topic/notificaciones-erp", noti);
    }
}
