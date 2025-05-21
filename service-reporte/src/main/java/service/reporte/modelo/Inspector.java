/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "inspectores")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspector")
    private Integer idInspector;

    private String nombre;

    private Boolean activo;

    public Inspector() {
    }

    public Inspector(Integer idInspector, String nombre, Boolean activo) {
        this.idInspector = idInspector;
        this.nombre = nombre;
        this.activo = activo;
    }

    
    public Integer getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(Integer idInspector) {
        this.idInspector = idInspector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    
}
