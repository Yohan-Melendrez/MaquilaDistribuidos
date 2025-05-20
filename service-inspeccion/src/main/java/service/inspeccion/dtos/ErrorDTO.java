/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.inspeccion.dtos;

import java.math.BigDecimal;
import service.inspeccion.modelo.ErrorProduccion;

/**
 *
 * @author Gabriel
 */
public class ErrorDTO {
    private Integer idError;
    private String nombre;
    private String descripcion;
    private BigDecimal costoUsd;
    private String nivelAtencion;

    public ErrorDTO(ErrorProduccion error) {
        this.idError = error.getIdError();
        this.nombre = error.getNombre();
        this.descripcion = error.getDescripcion();
        this.costoUsd = error.getCostoUsd();
        this.nivelAtencion = error.getNivelAtencion() != null ? error.getNivelAtencion().name() : null;
    }

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoUsd() {
        return costoUsd;
    }

    public void setCostoUsd(BigDecimal costoUsd) {
        this.costoUsd = costoUsd;
    }

    public String getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(String nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }


    
}
