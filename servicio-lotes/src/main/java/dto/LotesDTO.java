/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 *
 * @author Gabriel
 */
public class LotesDTO {

    private Long id;
    private String codigoLote;
    private String producto;
    private int cantidadTotal;
    private LocalDateTime fechaCreacion;
    private Long inspectorAsignadoId;

    public LotesDTO() {
    }

    public LotesDTO(Long id, String codigoLote, String producto, int cantidadTotal,
            LocalDateTime fechaCreacion, Long inspectorAsignadoId) {
        this.id = id;
        this.codigoLote = codigoLote;
        this.producto = producto;
        this.cantidadTotal = cantidadTotal;
        this.fechaCreacion = fechaCreacion;
        this.inspectorAsignadoId = inspectorAsignadoId;
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
