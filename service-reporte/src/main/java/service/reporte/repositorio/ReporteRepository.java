/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.repositorio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import service.reporte.modelo.Inspeccion;
import service.reporte.modelo.Reporte;

/**
 *
 * @author USER
 */
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    // Esto ya incluye findAll() automáticamente por JpaRepository
}

