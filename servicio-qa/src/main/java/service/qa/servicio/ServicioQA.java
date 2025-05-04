/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.modelo.Lote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.LotesDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.modelo.Inspector;
import service.qa.rabbit.ProductorNotificaciones;
import service.qa.repositorio.InspectorRepositorio;
import service.qa.repositorio.LoteRepositorio;
import service.qa.repositorio.NotificacionRepositorio;
import org.springframework.context.annotation.Lazy;
import service.qa.modelo.Notificacion;

/**
 *
 * @author Gabriel
 */
@Service
@Transactional
public class ServicioQA {

    private final LoteRepositorio loteRepo;
    private final InspectorRepositorio inspectorRepo;
    private final NotificacionRepositorio notificacionRepo;
    private final ProductorNotificaciones productor;
    private final RestTemplate restTemplate;

    // URL del servicio ERP para integración
    @Value("${erp.service.url:http://localhost:8082/erp}")
    private String erpServiceUrl;

    // Flag para habilitar/deshabilitar integración con ERP
    @Value("${erp.integration.enabled:false}")
    private boolean erpIntegrationEnabled;

  

public ServicioQA(LoteRepositorio loteRepo,
                  InspectorRepositorio inspectorRepo,
                  NotificacionRepositorio notificacionRepo,
                  @Lazy ProductorNotificaciones productor) {
    this.loteRepo = loteRepo;
    this.inspectorRepo = inspectorRepo;
    this.notificacionRepo = notificacionRepo;
    this.productor = productor;
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
            System.out.println("Nivel de atención '" + dto.getNivelAtencion() + "' asignado al lote ID: " + dto.getIdLote());
            // Integración con sistema ERP si está habilitado
            if (erpIntegrationEnabled) {
                enviarActualizacionAERP(dto);
            }
        } else {
            System.out.println(" No se encontró el lote con ID: " + dto.getIdLote());
        }
    }

    /**
     * Envía información de actualización al sistema ERP externo
     *
     * @param dto Datos de evaluación a enviar al ERP
     */
    private void enviarActualizacionAERP(EvaluacionDefectoDTO dto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EvaluacionDefectoDTO> request = new HttpEntity<>(dto, headers);

            // Endpoint para actualizar nivel de atención en el ERP
            String endpoint = erpServiceUrl + "/actualizarNivelAtencion";

            restTemplate.postForEntity(endpoint, request, String.class);

            System.out.println("Actualización enviada al sistema ERP para el lote ID: " + dto.getIdLote());
        } catch (Exception e) {
            System.err.println("Error al enviar actualización al sistema ERP: " + e.getMessage());
            // Aquí podrías implementar una cola de reintentos si es crítico que la actualización llegue al ERP
        }
    }

    public void asignarLoteAInspector(AsignacionLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote()).orElse(null);
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector()).orElse(null);

        if (lote == null) {
            System.err.println("No se encontró el lote con ID: " + dto.getIdLote());
            return; // O manejar el error como prefieras
        }

        if (inspector == null) {
            System.err.println("No se encontró el inspector con ID: " + dto.getIdInspector());
            return; // O manejar el error como prefieras
        }

        if (!inspector.getLotes().contains(lote)) {
            inspector.getLotes().add(lote);
            lote.setInspector(inspector); // clave
            loteRepo.save(lote); // guarda el lado propietario
        }

        NotificacionDTO notiDTO = new NotificacionDTO(
                "Nuevo lote asignado",
                "Se te ha asignado el lote: " + lote.getNombreLote(),
                "ASIGNACION",
                LocalDateTime.now(),
                inspector.getIdInspector()
        );

        productor.enviarNotificacion(notiDTO);

        Notificacion noti = new Notificacion();
        noti.setTitulo(notiDTO.getTitulo());
        noti.setMensaje(notiDTO.getMensaje());
        noti.setTipo(notiDTO.getTipo());
        noti.setFechaEnvio(notiDTO.getFechaEnvio());
        noti.setInspector(inspector);
        noti.setOrigen("QA");
        notificacionRepo.save(noti);
    }

    public void guardarNotificacion(NotificacionDTO dto) {
        Notificacion noti = new Notificacion();
        noti.setTitulo(dto.getTitulo());
        noti.setMensaje(dto.getMensaje());
        noti.setTipo(dto.getTipo());
        noti.setFechaEnvio(dto.getFechaEnvio());

        if (dto.getIdInspector() != null) {
            Inspector inspector = inspectorRepo.findById(dto.getIdInspector()).orElse(null);
            noti.setInspector(inspector); // si no hay, se guarda null
        }
        noti.setOrigen(dto.getOrigen());

        notificacionRepo.save(noti);
        if("QA".equalsIgnoreCase(dto.getOrigen()))
        {productor.enviarNotificacion(dto);} 
    }

    public void asignarLote(Integer idLote, Integer idInspector) {
        AsignacionLoteDTO dto = new AsignacionLoteDTO();
        dto.setIdInspector(idInspector);
        dto.setIdLote(idLote);
        asignarLoteAInspector(dto);
    }

    public List<LotesDTO> obtenerTodosLosLotes() {
        return loteRepo.findAll().stream()
                .map(LotesDTO::new)
                .collect(Collectors.toList());

    }

    public List<LotesDTO> obtenerLotesConErrores() {
        return loteRepo.findAll().stream()
                .filter(lote -> lote.getProductos().stream()
                .anyMatch(lp -> !lp.getProducto().getErrores().isEmpty()))
                .map(LotesDTO::new)
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNotificaciones() {
        return notificacionRepo.findAll().stream()
                .map(noti -> new NotificacionDTO(
                noti.getTitulo(),
                noti.getMensaje(),
                noti.getTipo(),
                noti.getFechaEnvio(),
                noti.getInspector() != null ? noti.getInspector().getIdInspector() : null
        ))
                .collect(Collectors.toList());
    }

    public void enviarLotesDeInspector(Integer idInspector) {
        Inspector inspector = inspectorRepo.findById(idInspector).orElse(null);

        if (inspector == null) {
            System.err.println("Inspector no encontrado con ID: " + idInspector);
            return;
        }
        List<LotesDTO> lotesDTO = inspector.getLotes().stream()
                .map(LotesDTO::new)
                .collect(Collectors.toList());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<LotesDTO>> request = new HttpEntity<>(lotesDTO, headers);

            String endpoint = erpServiceUrl + "/recibirLotesInspector";  // Ajusta al endpoint real

            restTemplate.postForEntity(endpoint, request, String.class);

            System.out.println("Lotes del inspector " + idInspector + " enviados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al enviar lotes del inspector: " + e.getMessage());
        }
    }

    public List<Inspector> obtenerInspectoresActivos() {
        return inspectorRepo.findAll().stream()
                .filter(Inspector::getActivo)
                .collect(Collectors.toList());
    }

}
