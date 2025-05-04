package service.inspeccion.dtos;
import java.time.LocalDateTime;

public class NotificacionDTO {

    private String titulo;
    private String mensaje;
    private String tipo;
    private Integer idInspector;
    private LocalDateTime fechaEnvio; // ← Agregar esto

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getIdInspector() { return idInspector; }
    public void setIdInspector(Integer idInspector) { this.idInspector = idInspector; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
