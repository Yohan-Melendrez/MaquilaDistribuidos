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
import service.reporte.dtos.ReporteDTO;
import service.reporte.modelo.Inspeccion;
import service.reporte.modelo.Reporte;
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

    public List<ReporteDTO> obtenerHistorial() {
        List<Reporte> reportes = reporteRepository.findAll();

        return reportes.stream().map(r -> new ReporteDTO(
                r.getTipoDefecto(),
                r.getTotalPiezasRechazadas(),
                r.getCostoTotalUsd(),
                r.getDetallesRechazo()
        )).toList();
    }
}
