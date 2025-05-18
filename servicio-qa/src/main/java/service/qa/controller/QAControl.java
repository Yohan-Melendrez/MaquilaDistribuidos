/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.controller;

import java.util.List;
import java.util.Map;

import service.qa.dto.AsignacionLoteDTO;
import service.qa.dto.EvaluacionDefectoDTO;
import service.qa.dto.NotificacionDTO;
import service.qa.modelo.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.qa.dto.LotesDTO;
import service.qa.modelo.Inspector;
import service.qa.servicio.ServicioQA;

@RestController
@RequestMapping("/qa")
@CrossOrigin(origins = "*")
public class QAControl {

    @Autowired
    private ServicioQA servicioQA;

//    // Endpoint para recibir notificación de defectos desde inspección
//    @PostMapping("/notificarDefecto")
//    public ResponseEntity<String> notificarDefecto(@RequestBody Lote lote) {
//        servicioQA.recibirNotificacionDefecto(lote);
//        return new ResponseEntity<>("Defecto Recibido", HttpStatus.CREATED);
//    }
    @PutMapping("/asignarNivelAtencion")
    public ResponseEntity<String> asignarNivelAtencion(@RequestBody EvaluacionDefectoDTO dto) {
        servicioQA.asignarNivelAtencion(dto);
        return ResponseEntity.ok("Nivel de atención actualizado");
    }

    @PostMapping("/asignarLote")
    public ResponseEntity<Map<String, String>> asignarLote(@RequestBody AsignacionLoteDTO dto) {
        servicioQA.asignarLoteAInspector(dto);
        return ResponseEntity.ok(Map.of("mensaje", "Lote asignado en QA"));
    }

// 
    @GetMapping("/lotes")
    public ResponseEntity<List<LotesDTO>> obtenerTodosLotes() {
        List<LotesDTO> lotes = servicioQA.obtenerTodosLosLotes();
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/lotesConErrores")
    public ResponseEntity<List<LotesDTO>> obtenerLotesConErrores() {
        List<LotesDTO> lotes = servicioQA.obtenerLotesConErrores();
        return ResponseEntity.ok(lotes);
    }

//    @PostMapping("/externa")
//    public ResponseEntity<String> recibirNotificacionExterna() {
//        servicioQA.guardarNotificacion(dto);
//        return ResponseEntity.ok("Notificación recibida y guardada.");
//    }
    @PostMapping(value = "/notificaciones")
    public ResponseEntity<String> Notificaciones(@RequestBody NotificacionDTO dto) {
        servicioQA.Notificaciones(dto);
        return ResponseEntity.ok("Notificación enviada");
    }
    // GET: obtener las notificaciones en memoria

    @GetMapping("/notificaciones")
    public ResponseEntity<List<NotificacionDTO>> obtenerNotificaciones() {
        return ResponseEntity.ok(servicioQA.obtenerNotificaciones());
    }

    @GetMapping("/inspectores")
    public List<Inspector> obtenerInspectoresActivos() {
        return servicioQA.obtenerInspectoresActivos();
    }

    @GetMapping("/lote/{id}")
    public ResponseEntity<LotesDTO> obtenerLotePorId(@PathVariable Integer id) {
        LotesDTO loteDTO = servicioQA.obtenerLotePorId(id);
        if (loteDTO != null) {
            return ResponseEntity.ok(loteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/recibirLote")
    public ResponseEntity<String> recibirLoteDesdeERP(@RequestBody LotesDTO dto) {
        servicioQA.recibirLoteDesdeERP(dto);
        return ResponseEntity.ok("Lote recibido correctamente");
    }
}
