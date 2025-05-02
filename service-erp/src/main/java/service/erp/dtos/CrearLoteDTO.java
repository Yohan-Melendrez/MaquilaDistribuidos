/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.dtos;

import java.util.List;

/**
 *
 * @author abelc
 */
public class CrearLoteDTO {
    private String nombreLote;
    private List<LoteItemDTO> productos;
    public String getNombreLote;

    public String getNombreLote() {
        return nombreLote;
    }

    public void setNombreLote(String nombreLote) {
        this.nombreLote = nombreLote;
    }

    public List<LoteItemDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<LoteItemDTO> productos) {
        this.productos = productos;
    }
}
