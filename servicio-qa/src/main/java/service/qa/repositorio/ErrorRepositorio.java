/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.qa.modelo.ErrorProduccion;
import service.qa.modelo.Producto;

/**
 *
 * @author abelc
 */
@Repository
public interface ErrorRepositorio extends JpaRepository<ErrorProduccion, Integer> {

    Optional<ErrorProduccion> findByDescripcion(String descripcion);

    @Query("SELECT e FROM ErrorProduccion e WHERE e.descripcion = :descripcion AND e.nombre = :nombre")
    ErrorProduccion findByDescripcionAndNombre(@Param("descripcion") String descripcion, @Param("nombre") String nombre);


}
