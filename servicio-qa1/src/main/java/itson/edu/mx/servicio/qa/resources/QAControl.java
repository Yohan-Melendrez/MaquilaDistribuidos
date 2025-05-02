/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.servicio.qa.resources;

import dto.AsignacionLoteDTO;
import dto.EvaluacionDefectoDTO;
import dto.NotificacionDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import modelo.Lote;
import servicio.ServicioQA;

/**
 *
 * @author Gabriel
 */
@Path("qa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QAControl {

    @Inject
    private ServicioQA servicioQA;

    // Endpoint para recibir notificación de defectos desde inspección
    @POST
    @Path("/notificarDefecto")
    public Response notificarDefecto(Lote lote) {
        servicioQA.recibirNotificacionDefecto(lote);
        return Response.status(Response.Status.CREATED).entity("Defecto Recibido").build();
    }

    @PUT
    @Path("/asignarNivelAtencion")
    public Response asignarNivelAtencion(EvaluacionDefectoDTO dto) {
        servicioQA.asignarNivelAtencion(dto);
        return Response.ok("Nivel de atención actualizado").build();
    }

//    @GET
//    @Path("/lotesConErrores")
//    public Response obtenerLotesConErrores() {
//        List<Lote> lotes = servicioQA.obtenerLotesConErrores();
//        return Response.ok(lotes).build(); // Se serializa a JSON automáticamente
//    }

    // Endpoint para asignar lotes a inspectores
    @POST
    @Path("/asignarLote")
    public Response asignarLoteAInspector(AsignacionLoteDTO dto) {
        servicioQA.asignarLoteAInspector(dto);
        return Response.status(Response.Status.CREATED).entity("Lote asignado al inspector").build();
    }

    // Endpoint para guardar una notificación manual
    @POST
    @Path("/guardarNotificacion")
    public Response guardarNotificacion(NotificacionDTO dto) {
        servicioQA.guardarNotificacion(dto);
        return Response.status(Response.Status.CREATED).entity("Notificación guardada").build();
    }
}
