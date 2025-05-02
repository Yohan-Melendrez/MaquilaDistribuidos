/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dto.AsignacionLoteDTO;
import dto.EvaluacionDefectoDTO;
import dto.NotificacionDTO;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import modelo.Inspector;
import modelo.Lote;
import modelo.Notificacion;
import repositorio.InspectorRepositorio;
import repositorio.LoteRepositorio;
import repositorio.NotificacionRepositorio;
import rabbit.ProductorNotificaciones;

/**
 *
 * @author Gabriel
 */
public class ServicioQA {

    @Inject
    private LoteRepositorio loteRepo;

    @Inject
    private InspectorRepositorio inspectorRepo;

    @Inject
    private NotificacionRepositorio notificacionRepo;

    @Inject
    private ProductorNotificaciones productor;

    public void recibirNotificacionDefecto(EvaluacionDefectoDTO dto) {
        if (dto.isRequiereAtencionInmediata()) {
            System.out.println("El error " + dto.getIdError() + " requiere atención inmediata.");

            NotificacionDTO noti = new NotificacionDTO();
            noti.setTitulo("Error crítico detectado");
            noti.setMensaje("El error " + dto.getIdError() + " requiere atención inmediata");
            noti.setTipo("DEFECTO_CRITICO");
            noti.setFechaEnvio(LocalDateTime.now());
            productor.enviarNotificacion(noti); // Enviar a la cola de RabbitMQ
        }
    }

    public void asignarLoteAInspector(AsignacionLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote());
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector());

        if (lote != null && inspector != null) {
            inspector.getLotes().add(lote);
            inspectorRepo.update(inspector);

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
            notificacionRepo.save(noti);
        }
    }

    public void guardarNotificacion(NotificacionDTO dto) {
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector());
        if (inspector != null) {
            Notificacion noti = new Notificacion();
            noti.setTitulo(dto.getTitulo());
            noti.setMensaje(dto.getMensaje());
            noti.setTipo(dto.getTipo());
            noti.setFechaEnvio(dto.getFechaEnvio());
            noti.setInspector(inspector);
            notificacionRepo.save(noti);
            productor.enviarNotificacion(dto);
        }
    }

    public void asignarLote(Integer idLote, Integer idInspector) {
        AsignacionLoteDTO dto = new AsignacionLoteDTO();
        dto.setIdInspector(idInspector);
        dto.setIdLote(idLote);
        asignarLoteAInspector(dto);
    }
}
