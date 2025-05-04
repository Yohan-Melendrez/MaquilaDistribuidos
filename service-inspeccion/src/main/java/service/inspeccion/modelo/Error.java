package service.inspeccion.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "errores")
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_error")
    private Integer idError;

    private String nombre;
    private String descripcion;

    @Column(name = "costo_usd")
    private Double costoUsd;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_atencion")
    private NivelAtencion nivelAtencion;

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

    public NivelAtencion getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(NivelAtencion nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }
}