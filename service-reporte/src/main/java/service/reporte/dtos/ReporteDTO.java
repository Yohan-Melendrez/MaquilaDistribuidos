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
    private String tipoDefecto;
    private Long totalPiezasRechazadas;
    private Double costoTotalUsd;
    private String detallesRechazo;

    public ReporteDTO() {
    }

    public ReporteDTO(String tipoDefecto, Long totalPiezasRechazadas, Double costoTotalUsd, String detallesRechazo) {
        this.tipoDefecto = tipoDefecto;
        this.totalPiezasRechazadas = totalPiezasRechazadas;
        this.costoTotalUsd = costoTotalUsd;
        this.detallesRechazo = detallesRechazo;
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
    
    
}
