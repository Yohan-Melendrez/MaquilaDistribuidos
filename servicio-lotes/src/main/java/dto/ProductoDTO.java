/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.List;

/**
 *
 * @author abelc
 */
public class ProductoDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private List<Integer> erroresIds; // Solo IDs para simplificar

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
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

    public List<Integer> getErroresIds() {
        return erroresIds;
    }

    public void setErroresIds(List<Integer> erroresIds) {
        this.erroresIds = erroresIds;
    }
    
    
}
