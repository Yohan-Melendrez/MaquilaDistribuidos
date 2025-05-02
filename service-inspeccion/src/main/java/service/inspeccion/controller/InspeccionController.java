package service.inspeccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.inspeccion.dtos.AsignarLoteDTO;
import service.inspeccion.dtos.ProductoDelLoteDTO;
import service.inspeccion.dtos.RegistroInspeccionDTO;
import service.inspeccion.modelo.Lote;
import service.inspeccion.servicio.InspeccionService;

@RestController
@RequestMapping("/inspeccion")
@CrossOrigin
public class InspeccionController {

    @Autowired
    private InspeccionService inspeccionService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarInspeccion(@RequestBody RegistroInspeccionDTO dto) {
        inspeccionService.registrarInspeccion(dto);
        return ResponseEntity.ok("Inspección registrada correctamente.");
    }

    @PostMapping("/asignar-lote")
    public ResponseEntity<?> asignarLote(@RequestBody AsignarLoteDTO dto) {
        inspeccionService.asignarLoteAInspector(dto);
        return ResponseEntity.ok("Lote asignado correctamente al inspector.");
    }

    @GetMapping("/lotes-asignados/{inspector}")
    public ResponseEntity<List<Lote>> obtenerLotes(@PathVariable String inspector) {
        List<Lote> lotes = inspeccionService.obtenerLotesPorInspector(inspector);
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/productos-del-lote/{idLote}")
    public ResponseEntity<List<ProductoDelLoteDTO>> obtenerProductos(@PathVariable Integer idLote) {
        List<ProductoDelLoteDTO> productos = inspeccionService.obtenerProductosDeLote(idLote);
        return ResponseEntity.ok(productos);
    }
}
