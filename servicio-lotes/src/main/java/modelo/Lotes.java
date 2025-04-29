/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "lotes")
public class Lotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigoLote;

    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    private int cantidadTotal;

    @Column
    private LocalDateTime fechaCreacion;

    @Column
    private Long inspectorAsignadoId;

    public Lotes() {
    }

    public Lotes(String codigoLote, String producto, int cantidadTotal) {
        this.codigoLote = codigoLote;
        this.producto = producto;
        this.cantidadTotal = cantidadTotal;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getInspectorAsignadoId() {
        return inspectorAsignadoId;
    }

    public void setInspectorAsignadoId(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }

}
