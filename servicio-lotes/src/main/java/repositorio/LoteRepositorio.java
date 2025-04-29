/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.util.List;
import modelo.Lotes;

/**
 *
 * @author Gabriel
 */
public interface LoteRepositorio {
    List<Lotes> findByInspectorAsignadoId(Long inspectorId);
    Lotes findByCodigoLote(String codigoLote);
    Lotes save(Lotes lot);
    List<Lotes> findAll();
    void delete(Lotes lot);
}  

