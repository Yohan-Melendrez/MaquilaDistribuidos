/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public class FiltroReporteDTO {
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Integer idError;

    public FiltroReporteDTO() {
    }

    public FiltroReporteDTO(LocalDateTime inicio, LocalDateTime fin, Integer idError) {
        this.inicio = inicio;
        this.fin = fin;
        this.idError = idError;
    }

    
    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    
    
}
