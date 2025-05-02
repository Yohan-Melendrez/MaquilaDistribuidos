/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import modelo.Inspector;

/**
 *
 * @author Gabriel
 */
public class InspectorRepositorio {

    @PersistenceContext
    private EntityManager em;

    public Inspector findById(Integer id) {
        return em.find(Inspector.class, id);
    }

    public void save(Inspector inspector) {
        em.persist(inspector);
    }

    public Inspector update(Inspector inspector) {
        return em.merge(inspector);
    }
}

