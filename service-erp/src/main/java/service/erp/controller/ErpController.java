/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.controller;

/**
 *
 * @author abelc
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import service.erp.dtos.CrearLoteDTO;
import service.erp.modelo.Lote;
import service.erp.modelo.Producto;
import service.erp.servicio.ErpService;

@RestController
@CrossOrigin // permite llamadas desde el front (ajusta si necesitas)
public class ErpController {

    @Autowired
    private ErpService erpService;

    // Obtener todos los productos
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(erpService.obtenerTodosProductos());
    }

    // Obtener todos los lotes existentes
    @GetMapping("/lotes")
    public ResponseEntity<List<Lote>> obtenerLotes() {
        return ResponseEntity.ok(erpService.obtenerTodosLotes());
    }

    // Crear nuevo lote
    @PostMapping("/lotes")
    public ResponseEntity<?> crearLote(@RequestBody CrearLoteDTO dto) {
        erpService.crearLote(dto);
        return ResponseEntity.ok("Lote creado correctamente");
    }
}
