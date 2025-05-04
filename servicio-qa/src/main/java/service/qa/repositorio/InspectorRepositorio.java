/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.repositorio;

import java.util.List;
import java.util.Optional;

import service.qa.modelo.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.qa.modelo.Lote;

/**
 *
 * @author Gabriel
 */
@Repository
public interface InspectorRepositorio extends JpaRepository<Inspector, Integer> {
    Optional<Inspector> findByNombre(String nombre);

}
