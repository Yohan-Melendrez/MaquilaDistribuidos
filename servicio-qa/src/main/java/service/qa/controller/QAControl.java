/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.controller;

import java.util.List;
import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.modelo.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.qa.servicio.ServicioQA;

@RestController
@RequestMapping("/qa")
public class QAControl {

    @Autowired
    private ServicioQA servicioQA;

    // Endpoint para recibir notificación de defectos desde inspección
    @PostMapping("/notificarDefecto")
    public ResponseEntity<String> notificarDefecto(@RequestBody Lote lote) {
        servicioQA.recibirNotificacionDefecto(lote);
        return new ResponseEntity<>("Defecto Recibido", HttpStatus.CREATED);
    }

    @PutMapping("/asignarNivelAtencion")
    public ResponseEntity<String> asignarNivelAtencion(@RequestBody EvaluacionDefectoDTO dto) {
        servicioQA.asignarNivelAtencion(dto);
        return ResponseEntity.ok("Nivel de atención actualizado");
    }

    // Endpoint para asignar lotes a inspectores
    @PostMapping("/asignarLote")
    public ResponseEntity<String> asignarLoteAInspector(@RequestBody AsignacionLoteDTO dto) {
        servicioQA.asignarLoteAInspector(dto);
        return new ResponseEntity<>("Lote asignado al inspector", HttpStatus.CREATED);
    }

    // Endpoint para guardar una notificación manual
    @PostMapping("/guardarNotificacion")
    public ResponseEntity<String> guardarNotificacion(@RequestBody NotificacionDTO dto) {
        servicioQA.guardarNotificacion(dto);
        return new ResponseEntity<>("Notificación guardada", HttpStatus.CREATED);
    }

    @GetMapping("/lotes")
    public ResponseEntity<List<Lote>> obtenerTodosLotes() {
        List<Lote> lotes = servicioQA.obtenerTodosLosLotes();
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/lotesConErrores")
    public ResponseEntity<List<Lote>> obtenerLotesConErrores() {
        List<Lote> lotes = servicioQA.obtenerLotesConErrores();
        return ResponseEntity.ok(lotes);
    }
}
