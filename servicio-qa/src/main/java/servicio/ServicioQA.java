/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dto.AsignacionLoteDTO;
import dto.EvaluacionDefectoDTO;
import dto.NotificacionDTO;
import modelo.ErrorProduccion;
import modelo.Inspector;
import modelo.Lote;
import modelo.LoteProducto;
import modelo.Notificacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rabbit.ProductorNotificaciones;
import repositorio.InspectorRepositorio;
import repositorio.LoteRepositorio;
import repositorio.NotificacionRepositorio;

import java.time.LocalDateTime;

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

    public ServicioQA(LoteRepositorio loteRepo,
            InspectorRepositorio inspectorRepo,
            NotificacionRepositorio notificacionRepo,
            ProductorNotificaciones productor) {
        this.loteRepo = loteRepo;
        this.inspectorRepo = inspectorRepo;
        this.notificacionRepo = notificacionRepo;
        this.productor = productor;
    }

    public void recibirNotificacionDefecto(Lote lote) {
        loteRepo.save(lote);
    }

    public void asignarNivelAtencion(EvaluacionDefectoDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote()).orElse(null);

        if (lote != null) {
            for (LoteProducto loteProducto : lote.getProductos()) {
                for (ErrorProduccion error : loteProducto.getProducto().getErrores()) {
                    if (error.getIdError().equals(dto.getIdError())) {
                        error.setNivelAtencion(dto.getNivelAtencion());
                        return;
                    }
                }
            }
            System.out.println("Asignado nivel: " + dto.getNivelAtencion()
                    + " al error: " + dto.getIdError() + " del lote: " + dto.getIdLote());
        }
    }

    public void asignarLoteAInspector(AsignacionLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote()).orElse(null);
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector()).orElse(null);

        if (lote != null && inspector != null) {
            inspector.getLotes().add(lote);
            inspectorRepo.save(inspector);

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
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector()).orElse(null);
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
