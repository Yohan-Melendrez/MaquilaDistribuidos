/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abelc
 */
@Entity
@Table(name = "errores")
public class ErrorProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idError;

    private String nombre;

    private String descripcion;

    @Column(name = "costo_usd", precision = 10, scale = 2)
    private BigDecimal costoUsd;

    @ManyToMany(mappedBy = "errores")
    private List<Producto> productos = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_atencion")
    private NivelAtencion nivelAtencion;
    // Getters y Setters

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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public NivelAtencion getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(NivelAtencion nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }
    
}
