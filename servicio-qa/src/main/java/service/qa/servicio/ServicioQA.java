package service.qa.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.dto.LotesDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.modelo.Inspector;
import service.qa.modelo.Lote;
import service.qa.modelo.Notificacion;
import service.qa.rabbit.ProductorNotificaciones;
import service.qa.repositorio.InspectorRepositorio;
import service.qa.repositorio.LoteRepositorio;
import service.qa.repositorio.NotificacionRepositorio;

@Service
@Transactional
public class ServicioQA {

    private final LoteRepositorio loteRepo;
    private final InspectorRepositorio inspectorRepo;
    private final NotificacionRepositorio notificacionRepo;
    private final RestTemplate restTemplate;

    @Value("${erp.integration.enabled:false}")
    private boolean erpIntegrationEnabled;

    @Value("${erp.service.url:http://localhost:8082/erp}")
    private String erpServiceUrl;

    public ServicioQA(
        LoteRepositorio loteRepo,
        InspectorRepositorio inspectorRepo,
        NotificacionRepositorio notificacionRepo,
        @Lazy ProductorNotificaciones productor
    ) {
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

    public void asignarLoteAInspector(AsignacionLoteDTO dto) {
        if (dto.getIdLote() == null || dto.getIdInspector() == null) {
          throw new IllegalArgumentException("El ID del lote o del inspector no puede ser null");
        }
    
        // 1) Levantar ambas entidades
        Lote lote = loteRepo.findById(dto.getIdLote())
            .orElseThrow(() -> new RuntimeException("No se encontró el lote " + dto.getIdLote()));
        Inspector insp = inspectorRepo.findById(dto.getIdInspector())
            .orElseThrow(() -> new RuntimeException("No se encontró el inspector " + dto.getIdInspector()));
    
        // 2) Asignar y desactivar
        lote.setInspector(insp);
        loteRepo.save(lote);
    
        insp.setActivo(false);
        inspectorRepo.save(insp);
      }

    public void guardarNotificacion(NotificacionDTO dto) {
        Notificacion noti = new Notificacion();
        noti.setTitulo(dto.getTitulo());
        noti.setMensaje(dto.getMensaje());
        noti.setTipo(dto.getTipo());
        noti.setFechaEnvio(dto.getFechaEnvio());
        if (dto.getIdInspector() != null) {
            inspectorRepo.findById(dto.getIdInspector())
                .ifPresent(noti::setInspector);
        }
        notificacionRepo.save(noti);
    }

    public List<LotesDTO> obtenerTodosLosLotes() {
        return loteRepo.findAll().stream()
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
        return notificacionRepo.findAll().stream()
                .map(n -> new NotificacionDTO(
                        n.getTitulo(),
                        n.getMensaje(),
                        n.getTipo(),
                        n.getFechaEnvio(),
                        n.getInspector() != null ? n.getInspector().getIdInspector() : null
                ))
                .collect(Collectors.toList());
    }

    public List<Inspector> obtenerInspectoresActivos() {
        return inspectorRepo.findAll().stream()
                .filter(Inspector::getActivo)
                .collect(Collectors.toList());
    }
}
