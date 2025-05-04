/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.dto;

import java.util.List;
import service.qa.modelo.Lote;

/**
 *
 * @author Gabriel
 */
public class LotesDTO {
    private Integer idLote;
    private String nombreLote;
    private String estado;
    private List<ProductoDTO> productos;

    public LotesDTO(Lote lote) {
        this.idLote = lote.getIdLote();
        this.nombreLote = lote.getNombreLote();
        this.estado = lote.getEstado();
        this.productos = lote.getProductos().stream()
            .map(lp -> new ProductoDTO(lp.getProducto(), lp.getCantidad()))
            .toList();
    }

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

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
    
}

    

