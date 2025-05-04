/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.reporte.dtos.ErrorDTO;
import service.reporte.dtos.FiltroReporteDTO;
import service.reporte.dtos.ReporteDTO;
import service.reporte.modelo.Reporte;
import service.reporte.servicio.ReporteService;

/**
 *
 * @author USER
 */
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/historial")
    public List<ReporteDTO> obtenerHistorialCompleto() {
        return reporteService.obtenerHistorial();
    }

    @PostMapping("/generarReporte")
    public Reporte crearReporte(@RequestBody FiltroReporteDTO filtro) {
        return reporteService.generarYGuardarReporte(filtro.getInicio(), filtro.getFin(), filtro.getIdError());
    }

    @GetMapping("/errores")
    public List<ErrorDTO> listarErrores() {
        return reporteService.listarErrores();
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<ReporteDTO> obtenerReportePorId(@PathVariable Long id) {
        ReporteDTO reporte = reporteService.obtenerReportePorId(id);
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
