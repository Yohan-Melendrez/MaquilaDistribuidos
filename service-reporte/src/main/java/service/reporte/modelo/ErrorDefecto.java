package service.reporte.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "error_posible")
public class ErrorDefecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_error")
    private Integer idError;

    private String nombre;
    private String descripcion;

    @Column(name = "costo")
    private Double costoUsd;

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
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

    public Double getCostoUsd() {
        return costoUsd;
    }

    public void setCostoUsd(Double costoUsd) {
        this.costoUsd = costoUsd;
    }
}