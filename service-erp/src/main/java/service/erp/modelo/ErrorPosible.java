/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * @author abelc
 */
@Entity
@Table(name = "errores")
public class ErrorPosible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idError;

    private String descripcion;
    private BigDecimal costo_usd;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference
    private Producto producto;

    // Getters y setters
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

  

    public BigDecimal getCosto_usd() {
        return costo_usd;
    }

    public void setCosto_usd(BigDecimal costo_usd) {
        this.costo_usd = costo_usd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
