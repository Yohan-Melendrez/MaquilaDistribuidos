package service.inspeccion.dtos;

public class ProductoDelLoteDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Integer cantidad;

    public ProductoDelLoteDTO() {}

    public ProductoDelLoteDTO(Integer idProducto, String nombre, String descripcion, Integer cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
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
}
