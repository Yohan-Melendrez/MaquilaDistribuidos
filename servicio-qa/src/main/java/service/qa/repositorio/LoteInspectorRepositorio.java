/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.qa.modelo.LoteInspector;

/**
 *
 * @author Gabriel
 */
@Repository
public interface LoteInspectorRepositorio extends JpaRepository<LoteInspector, Integer> {
}

