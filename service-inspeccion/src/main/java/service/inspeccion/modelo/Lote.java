// Lote.java
package service.inspeccion.modelo;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_atencion")
    private NivelAtencion nivelAtencion;

    @ManyToOne
    @JoinColumn(name = "id_inspector") 
    private Inspector inspector;

    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

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

    public NivelAtencion getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(NivelAtencion nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }
}