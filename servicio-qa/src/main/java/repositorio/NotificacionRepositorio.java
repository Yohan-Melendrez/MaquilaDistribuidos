/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import modelo.Notificacion;

/**
 *
 * @author Gabriel
 */
public class NotificacionRepositorio {

    @PersistenceContext
    private EntityManager em;

    public void save(Notificacion notificacion) {
        em.persist(notificacion);
    }
}
