// Lote.java
package service.reporte.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @Column(name = "nombre_lote")
    private String nombreLote;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_inspector")
    private Inspector inspector;

    @Column(name = "nivel_atencion")
    private String nivelAtencion;

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNombreLote() {
        return nombreLote;
    }

    public void setNombreLote(String nombreLote) {
        this.nombreLote = nombreLote;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
