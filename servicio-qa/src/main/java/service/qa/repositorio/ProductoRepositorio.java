/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.qa.modelo.Producto;

/**
 *
 * @author abelc
 */
@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    
    Optional<Producto> findByNombre(String nombre); 

}
