// Inspeccion.java
package service.reporte.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inspecciones")
public class Inspeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion")
    private Integer idInspeccion;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_error")
    private ErrorDefecto errorDefecto;

    private String inspector;

    private LocalDateTime fecha;

    @Column(name = "detalle_error")
    private String detalleError;

    @PrePersist
    public void setFechaPorDefecto() {
        this.fecha = LocalDateTime.now();
    }

    public Integer getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(Integer idInspeccion) {
        this.idInspeccion = idInspeccion;
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


    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public ErrorDefecto getErrorDefecto() {
        return errorDefecto;
    }

    public void setErrorDefecto(ErrorDefecto errorDefecto) {
        this.errorDefecto = errorDefecto;
    }

    public String getDetalleError() {
        return detalleError;
    }

    public void setDetalleError(String detalleError) {
        this.detalleError = detalleError;
    }

   

}
