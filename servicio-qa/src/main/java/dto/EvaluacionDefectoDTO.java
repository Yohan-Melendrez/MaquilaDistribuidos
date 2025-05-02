/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Gabriel
 */
public class EvaluacionDefectoDTO {

    private Integer idError;
    private boolean requiereAtencionInmediata;

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    public boolean isRequiereAtencionInmediata() {
        return requiereAtencionInmediata;
    }

    public void setRequiereAtencionInmediata(boolean requiereAtencionInmediata) {
        this.requiereAtencionInmediata = requiereAtencionInmediata;
    }

}
