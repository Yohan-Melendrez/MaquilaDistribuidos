/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.reporte.modelo.ErrorDefecto;

/**
 *
 * @author USER
 */

public interface ErrorRepository extends JpaRepository<ErrorDefecto, Integer> {
    
}
