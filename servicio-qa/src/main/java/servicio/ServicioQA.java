/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dto.AsignacionLoteDTO;
import dto.EvaluacionDefectoDTO;
import dto.NotificacionDTO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import modelo.ErrorProduccion;
import modelo.Inspector;
import modelo.Lote;
import modelo.LoteProducto;
import modelo.Notificacion;
import repositorio.InspectorRepositorio;
import repositorio.LoteRepositorio;
import repositorio.NotificacionRepositorio;
import rabbit.ProductorNotificaciones;

/**
 *
 * @author Gabriel
 */
@Stateless
public class ServicioQA {

    @Inject
    private LoteRepositorio loteRepo;

    @Inject
    private InspectorRepositorio inspectorRepo;

    @Inject
    private NotificacionRepositorio notificacionRepo;

    @Inject
    private ProductorNotificaciones productor;

    public void recibirNotificacionDefecto(Lote lote) {
        loteRepo.save(lote);
    }

    public void asignarNivelAtencion(EvaluacionDefectoDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote());

        if (lote != null) {
            for (LoteProducto loteProducto : lote.getProductos()) {
                for (ErrorProduccion error : loteProducto.getProducto().getErrores()) {
                    if (error.getIdError().equals(dto.getIdError())) {
                        error.setNivelAtencion(dto.getNivelAtencion());
                        return;
                    }
                }
            }
        }
        System.out.println("Asignado nivel: " + dto.getNivelAtencion()
                + " al error: " + dto.getIdError() + " del lote: " + dto.getIdLote());

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

//    public List<Lote> obtenerLotesConErrores() {
//        return loteRepo.findAllConErrores(); // o como sea que lo tengas implementado
//    }
}
