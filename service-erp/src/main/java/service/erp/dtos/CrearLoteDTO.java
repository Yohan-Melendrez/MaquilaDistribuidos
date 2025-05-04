/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author abelc
 */
public class CrearLoteDTO implements Serializable{
    private Integer idLote;
    private String nombreLote;
    private String estado;
    private List<LoteItemDTO> productos;

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

    public List<LoteItemDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<LoteItemDTO> productos) {
        this.productos = productos;
    }

}
