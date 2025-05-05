/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.dtos;

/**
 *
 * @author USER
 */
public class ReporteDTO {
    private Long id;
    private String tipoDefecto;
    private Long totalPiezasRechazadas;
    private Double costoTotalUsd;
    private Double costoTotalMxn;
    private String detallesRechazo;
    private String fechaComprendida;

    public ReporteDTO() {
    }

    public ReporteDTO(Long id, String tipoDefecto, Long totalPiezasRechazadas, Double costoTotalUsd, Double costoTotalMxn, String detallesRechazo, String fechaComprendida) {
        this.id = id;
        this.tipoDefecto = tipoDefecto;
        this.totalPiezasRechazadas = totalPiezasRechazadas;
        this.costoTotalUsd = costoTotalUsd;
        this.costoTotalMxn = costoTotalMxn;
        this.detallesRechazo = detallesRechazo;
        this.fechaComprendida = fechaComprendida;
    }

    public ReporteDTO(String tipoDefecto, Long totalPiezasRechazadas, Double costoTotalUsd, Double costoTotalMxn, String detallesRechazo, String fechaComprendida) {
        this.tipoDefecto = tipoDefecto;
        this.totalPiezasRechazadas = totalPiezasRechazadas;
        this.costoTotalUsd = costoTotalUsd;
        this.costoTotalMxn = costoTotalMxn;
        this.detallesRechazo = detallesRechazo;
        this.fechaComprendida = fechaComprendida;
    }

    public String getFechaComprendida() {
        return fechaComprendida;
    }

    public void setFechaComprendida(String fechaComprendida) {
        this.fechaComprendida = fechaComprendida;
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
    
    public Double getCostoTotalMxn() {
        return costoTotalMxn;
    }

    public void setCostoTotalMxn(Double costoTotalMxn) {
        this.costoTotalMxn = costoTotalMxn;
    }

    public String getDetallesRechazo() {
        return detallesRechazo;
    }

    public void setDetallesRechazo(String detallesRechazo) {
        this.detallesRechazo = detallesRechazo;
    }
    
    
}
