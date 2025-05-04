/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Gabriel
 */
public class NotificacionDTO implements Serializable {

    private String titulo;
    private String origen;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaEnvio;
    private Integer idInspector;

    public NotificacionDTO() {
    }

    public NotificacionDTO(String titulo, String mensaje, String tipo, LocalDateTime fechaEnvio, Integer idInspector) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fechaEnvio = fechaEnvio;
        this.idInspector = idInspector;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(Integer idInspector) {
        this.idInspector = idInspector;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

}
