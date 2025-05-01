/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dto.LotesDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import servicio.ServicioErp;

/**
 *
 * @author Gabriel
 */
@Path("/lotes") // URL base: /api/lotes, si el app tiene basePath /api
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LotesControl {

    @Inject
    private ServicioErp servicioLotes;

    @POST
    public Response crearLote(LotesDTO loteDTO) {
        LotesDTO nuevoLote = servicioLotes.crearLote(loteDTO);
        return Response.status(Response.Status.CREATED).entity(nuevoLote).build();
    }

    

}
