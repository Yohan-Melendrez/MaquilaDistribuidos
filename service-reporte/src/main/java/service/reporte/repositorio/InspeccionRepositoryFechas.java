/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.repositorio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import service.reporte.modelo.Inspeccion;
import service.reporte.modelo.Reporte;

/**
 *
 * @author USER
 */
public interface InspeccionRepositoryFechas extends JpaRepository<Inspeccion, Integer> {

    @Query("SELECT i FROM Inspeccion i WHERE i.fecha BETWEEN :inicio AND :fin")
    List<Inspeccion> findByFechaAndTipo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
