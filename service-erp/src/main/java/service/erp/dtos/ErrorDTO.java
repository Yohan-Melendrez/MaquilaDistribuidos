/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import service.erp.modelo.ErrorPosible;

/**
 *
 * @author abelc
 */
public class ErrorDTO implements Serializable {

    private Integer idError;
    private String descripcion;
    private BigDecimal costo;
    private String nombre;

    public ErrorDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public ErrorDTO(ErrorPosible error) {
        this.idError = error.getIdError();
        this.descripcion = error.getDescripcion();
        this.costo = error.getCosto_usd();
        this.nombre=error.getNombre();
    }

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

}
