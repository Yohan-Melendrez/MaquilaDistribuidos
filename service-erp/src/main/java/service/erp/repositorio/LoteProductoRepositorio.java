/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.erp.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.erp.modelo.LoteProducto;
import service.erp.modelo.LoteProductoId;

/**
 *
 * @author abelc
 */
public interface LoteProductoRepositorio extends JpaRepository<LoteProducto, LoteProductoId>{
    
}
