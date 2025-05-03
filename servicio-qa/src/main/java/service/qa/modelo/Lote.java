/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abelc
 */
@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLote;

    private String nombreLote;

    private String estado = "En proceso";

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    
    private List<LoteProducto> productos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_inspector")
    @JsonBackReference
    private Inspector inspector;


    // Getters y Setters
    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNombreLote() {
        return nombreLote;
    }

    public void setNombreLote(String nombreLote) {
        this.nombreLote = nombreLote;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<LoteProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<LoteProducto> productos) {
        this.productos = productos;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

//    public List<ErrorProduccion> getErrores() {
//        return errores;
//    }
//
//    public void setErrores(List<ErrorProduccion> errores) {
//        this.errores = errores;
//    }

}
