/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import modelo.Lote;

/**
 *
 * @author Gabriel
 */
public class LoteRepositorio {

    @PersistenceContext
    private EntityManager em;

    public Lote findById(Integer id) {
        return em.find(Lote.class, id);
    }

    public void save(Lote lote) {
        em.persist(lote);
    }

    public Lote update(Lote lote) {
        return em.merge(lote);
    }
    
}
