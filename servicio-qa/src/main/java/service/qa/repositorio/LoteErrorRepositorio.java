/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.qa.modelo.Lote;
import service.qa.modelo.LoteError;
import service.qa.modelo.LoteErrorId;

/**
 *
 * @author Gabriel
 */
public interface LoteErrorRepositorio extends JpaRepository<LoteError, LoteErrorId> {

    @Query(value = """
    SELECT DISTINCT l.* 
    FROM lotes l
    JOIN lote_productos lp ON l.id_lote = lp.id_lote
    JOIN producto_errores pe ON lp.id_producto = pe.id_producto
    """, nativeQuery = true)
    List<Lote> findLotesConProductosConErrores();
}
