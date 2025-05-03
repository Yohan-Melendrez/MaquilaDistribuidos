/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 *
 * @author abelc
 */
@Entity
@Table(name = "lote_productos")
public class LoteProducto {
 @EmbeddedId
    private LoteProductoId id = new LoteProductoId();

    @ManyToOne
    @MapsId("idLote")
    @JoinColumn(name = "id_lote")
    @JsonBackReference
    private Lote lote;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    @JsonBackReference
    private Producto producto;

    private Integer cantidad;


    // Getters y setters
    public LoteProductoId getId() {
        return id;
    }
    public void setId(LoteProductoId id) {
        this.id = id;
    }
    public Lote getLote() {
        return lote;
    }
    public void setLote(Lote lote) {
        this.lote = lote;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
