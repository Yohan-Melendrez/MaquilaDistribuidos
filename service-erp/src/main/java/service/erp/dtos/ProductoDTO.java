/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.dtos;

import java.util.List;
import java.util.stream.Collectors;
import service.erp.modelo.Producto;

/**
 *
 * @author Gabriel
 */
public class ProductoDTO {

    private Integer idProducto;
    private String nombre;
    private List<ErrorDTO> errores;

    public ProductoDTO() {
    }

    public ProductoDTO(Integer idProducto, String nombre, List<ErrorDTO> errores) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.errores = errores;
    }

    public ProductoDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
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

    public List<ErrorDTO> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorDTO> errores) {
        this.errores = errores;
    }

}
