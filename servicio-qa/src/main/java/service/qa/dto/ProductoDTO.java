/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.dto;

import java.util.List;
import java.util.stream.Collectors;
import service.qa.modelo.Producto;

/**
 *
 * @author Gabriel
 */
public class ProductoDTO {

    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private List<ErrorDTO> errores;

    public ProductoDTO() {
    }

    public ProductoDTO(Producto producto, Integer cantidad) {
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.cantidad = cantidad;
        this.errores = producto.getErrores().stream()
                .map(ErrorDTO::new)
                .collect(Collectors.toList());
    }

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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<ErrorDTO> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorDTO> errores) {
        this.errores = errores;
    }
    
}
