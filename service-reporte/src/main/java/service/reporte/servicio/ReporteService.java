/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.servicio;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.reporte.dtos.ErrorDTO;
import service.reporte.dtos.ReporteDTO;
import service.reporte.modelo.Inspeccion;
import service.reporte.modelo.Reporte;
import service.reporte.modelo.ErrorDefecto;
import service.reporte.repositorio.ErrorRepository;
import service.reporte.repositorio.InspeccionRepository;
import service.reporte.repositorio.ReporteRepository;

/**
 *
 * @author USER
 */
@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private InspeccionRepository inspeccionRepository;

    @Autowired
    private ErrorRepository errorRepository;

    public List<ReporteDTO> obtenerHistorial() {
        List<Reporte> reportes = reporteRepository.findAll();

        return reportes.stream().map(r -> new ReporteDTO(
                r.getId(),
                r.getTipoDefecto(),
                r.getTotalPiezasRechazadas(),
                r.getCostoTotalUsd(),
                r.getDetallesRechazo()
        )).toList();
    }

    public Reporte generarYGuardarReporte(LocalDateTime inicio, LocalDateTime fin, Integer idError) {
        List<Inspeccion> inspecciones = inspeccionRepository.findByFechaAndTipoError(inicio, fin, idError);

        if (inspecciones.isEmpty()) {
            throw new RuntimeException("No se encontraron inspecciones para los criterios dados.");
        }

        long total = inspecciones.size();
        double costoTotalUsd = inspecciones.stream()
                .mapToDouble(i -> i.getError().getCostoUsd())
                .sum();

        String detalles = inspecciones.stream()
                .map(i -> "El producto " + i.getProducto().getNombre()
                + " del lote " + i.getLote().getNombreLote()
                + " tiene el detalle " + i.getDetalle_Error())
                .collect(Collectors.joining(" | "));

        Reporte reporte = new Reporte();
        reporte.setTipoDefecto(inspecciones.get(0).getErrorDefecto().getNombre());
        reporte.setTotalPiezasRechazadas(total);
        reporte.setCostoTotalUsd(costoTotalUsd);
        reporte.setDetallesRechazo(detalles);

        return reporteRepository.save(reporte);
    }

    public List<ErrorDTO> listarErrores() {
        List<ErrorDefecto> errores = errorRepository.findAll();

        return errores.stream().map(e -> new ErrorDTO(
                e.getIdError(),
                e.getNombre()
        )).toList();
    }

    public ReporteDTO obtenerReportePorId(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));

        return new ReporteDTO(
                reporte.getTipoDefecto(), 
                reporte.getTotalPiezasRechazadas(), 
                reporte.getCostoTotalUsd(), 
                reporte.getDetallesRechazo());
    }
}
