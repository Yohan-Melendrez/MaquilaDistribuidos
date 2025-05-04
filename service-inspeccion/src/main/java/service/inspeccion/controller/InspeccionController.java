package service.inspeccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.inspeccion.dtos.AsignarLoteDTO;
import service.inspeccion.dtos.InspectorDTO;
import service.inspeccion.dtos.NotificacionDTO;
import service.inspeccion.dtos.ProductoDelLoteDTO;
import service.inspeccion.dtos.RegistroInspeccionDTO;
import service.inspeccion.modelo.ErrorProduccion;
import service.inspeccion.modelo.Lote;
import service.inspeccion.rabbit.ProductorNotificaciones;
import service.inspeccion.servicio.InspeccionService;

@RestController
@RequestMapping("/inspeccion")
@CrossOrigin(origins = "*")
public class InspeccionController {

    @Autowired
    private InspeccionService inspeccionService;

    @Autowired
    private ProductorNotificaciones productor;

    @GetMapping("/inspectores")
    public ResponseEntity<List<InspectorDTO>> obtenerInspectores() {
        return ResponseEntity.ok(inspeccionService.obtenerTodosLosInspectores());
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarInspeccion(@RequestBody RegistroInspeccionDTO dto) {
        inspeccionService.registrarInspeccion(dto);
        return ResponseEntity.ok("Inspección registrada correctamente.");
    }

    @PostMapping("/asignar-lote")
    public ResponseEntity<?> asignarLote(@RequestBody AsignarLoteDTO dto) {
        inspeccionService.asignarLoteAInspector(dto);
        return ResponseEntity.ok("Solicitud de asignación enviada a QA.");
    }

    @GetMapping("/lotes-asignados/{inspectorId}")
    public ResponseEntity<List<Lote>> obtenerLotes(@PathVariable Integer inspectorId) {
        return ResponseEntity.ok(inspeccionService.obtenerLotesPorInspector(inspectorId));
    }

    @GetMapping("/productos-del-lote/{idLote}")
    public ResponseEntity<List<ProductoDelLoteDTO>> obtenerProductos(@PathVariable Integer idLote) {
        return ResponseEntity.ok(inspeccionService.obtenerProductosDeLote(idLote));
    }

    @GetMapping("/errores")
    public ResponseEntity<List<ErrorProduccion>> obtenerErrores() {
        return ResponseEntity.ok(inspeccionService.obtenerTodosLosErrores());
    }

    @GetMapping("/errores/{idProducto}")
    public ResponseEntity<List<ErrorProduccion>> obtenerErroresPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(inspeccionService.obtenerErroresPorProducto(idProducto));
    }

    @PostMapping("/probar-notificacion")
    public ResponseEntity<?> probarNotificacion(@RequestBody NotificacionDTO dto) {
        productor.enviarNotificacion(dto);
        return ResponseEntity.ok("Notificacion enviada a la cola: " + dto.getTitulo() + " - " + dto.getMensaje());
    }
}
