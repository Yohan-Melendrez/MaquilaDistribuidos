/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.erp.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.erp.modelo.Lote;

/**
 *
 * @author abelc
 */
public interface LoteRepositorio extends JpaRepository<Lote, Integer> {
    
}
