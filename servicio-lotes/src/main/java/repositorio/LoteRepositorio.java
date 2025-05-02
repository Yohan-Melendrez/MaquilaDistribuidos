/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.util.List;
import modelo.Lote;

/**
 *
 * @author Gabriel
 */
public interface LoteRepositorio {
    List<Lote> findByInspectorAsignadoId(Long inspectorId);
    Lote findByCodigoLote(String codigoLote);
    Lote save(Lote lot);
    List<Lote> findAll();
    void delete(Lote lot);
}  

