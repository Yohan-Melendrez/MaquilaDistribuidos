/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class LotesDTO {

    private Integer idLote;
    private String nombreLote;
    private String estado;
    private List<LoteProductoDTO> productos;

    public LotesDTO(Integer idLote, String nombreLote, String estado, List<LoteProductoDTO> productos) {
        this.idLote = idLote;
        this.nombreLote = nombreLote;
        this.estado = estado;
        this.productos = productos;
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

    public List<LoteProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<LoteProductoDTO> productos) {
        this.productos = productos;
    }

    
}
