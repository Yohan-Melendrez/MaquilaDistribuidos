/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author abelc
 */
import java.math.BigDecimal;

public class ErrorProduccionDTO {
    private Integer idError;
    private String nombre;
    private String descripcion;
    private BigDecimal costoUsd;

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
    
}