// Inspeccion.java
package service.inspeccion.modelo;

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
    private ErrorProduccion error;

    @ManyToOne
    @JoinColumn(name = "id_inspector")
    private Inspector inspector;

    private LocalDateTime fecha;
    @Column(name = "detalle_error")
    private String detalle_error;

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

    public ErrorProduccion getError() {
        return error;
    }

    public void setError(ErrorProduccion error) {
        this.error = error;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDetalle_error() {
        return detalle_error;
    }

    public void setDetalle_error(String detalle_error) {
        this.detalle_error = detalle_error;
    }

}
