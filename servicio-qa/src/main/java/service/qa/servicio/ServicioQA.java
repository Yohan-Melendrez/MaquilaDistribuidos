/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.modelo.Lote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.modelo.Inspector;
import service.qa.rabbit.ProductorNotificaciones;
import service.qa.repositorio.InspectorRepositorio;
import service.qa.repositorio.LoteRepositorio;
import service.qa.repositorio.NotificacionRepositorio;

import service.qa.modelo.LoteError;
import service.qa.modelo.LoteErrorId;
import service.qa.modelo.Notificacion;
import service.qa.repositorio.LoteErrorRepositorio;

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
    private LoteErrorRepositorio loteErrorRepo;

    public ServicioQA(LoteRepositorio loteRepo, InspectorRepositorio inspectorRepo,
            NotificacionRepositorio notificacionRepo, ProductorNotificaciones productor,
            LoteErrorRepositorio loteErrorRepo) {
        this.loteRepo = loteRepo;
        this.inspectorRepo = inspectorRepo;
        this.notificacionRepo = notificacionRepo;
        this.productor = productor;
        this.loteErrorRepo = loteErrorRepo;
    }

    public void recibirNotificacionDefecto(Lote lote) {
        loteRepo.save(lote);
    }

    public void asignarNivelAtencion(EvaluacionDefectoDTO dto) {
        LoteErrorId id = new LoteErrorId();
        id.setIdLote(dto.getIdLote());
        id.setIdError(dto.getIdError());

        LoteError loteError = loteErrorRepo.findById(id).orElse(null);

        if (loteError != null) {
            loteError.setNivelAtencion(dto.getNivelAtencion());
            loteErrorRepo.save(loteError);

            System.out.println("Asignado nivel: " + dto.getNivelAtencion()
                    + " al error: " + dto.getIdError() + " del lote: " + dto.getIdLote());
        } else {
            System.out.println("️ No se encontró el registro lote_error con idLote=" + dto.getIdLote()
                    + " y idError=" + dto.getIdError() + ". Asegúrate de haberlo creado antes.");
        }
    }

    public void asignarLoteAInspector(AsignacionLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote()).orElse(null);
        Inspector inspector = inspectorRepo.findById(dto.getIdInspector()).orElse(null);

        if (lote != null && inspector != null) {
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

    public List<Lote> obtenerTodosLosLotes() {
        return loteRepo.findAll();
    }

    public List<Lote> obtenerLotesConErrores() {
        return loteErrorRepo.findLotesConProductosConErrores();
    }
}
