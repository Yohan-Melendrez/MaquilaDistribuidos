/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoDefecto;
    private Long totalPiezasRechazadas;
    private Double costoTotalUsd;
    private Double costoTotalMxn;

    @Lob
    private String detallesRechazo;

    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;

    @PrePersist
    public void prePersist() {
        this.fechaGeneracion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDefecto() {
        return tipoDefecto;
    }

    public void setTipoDefecto(String tipoDefecto) {
        this.tipoDefecto = tipoDefecto;
    }

    public Long getTotalPiezasRechazadas() {
        return totalPiezasRechazadas;
    }

    public void setTotalPiezasRechazadas(Long totalPiezasRechazadas) {
        this.totalPiezasRechazadas = totalPiezasRechazadas;
    }

    public Double getCostoTotalUsd() {
        return costoTotalUsd;
    }

    public void setCostoTotalUsd(Double costoTotalUsd) {
        this.costoTotalUsd = costoTotalUsd;
    }

    public String getDetallesRechazo() {
        return detallesRechazo;
    }

    public void setDetallesRechazo(String detallesRechazo) {
        this.detallesRechazo = detallesRechazo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Double getCostoTotalMxn() {
        return costoTotalMxn;
    }

    public void setCostoTotalMxn(Double costoTotalMxn) {
        this.costoTotalMxn = costoTotalMxn;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

}
