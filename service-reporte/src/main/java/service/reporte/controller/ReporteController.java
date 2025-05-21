/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/generarReporte")
    public ResponseEntity<List<ReporteDTO>> generarReportes(@RequestBody FiltroReporteDTO filtro) {
        LocalDateTime inicio = filtro.getInicio();
        LocalDateTime fin = filtro.getFin();

        List<ReporteDTO> reportes = reporteService.generarReportesPorFechas(inicio, fin);
        return ResponseEntity.ok(reportes);
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
