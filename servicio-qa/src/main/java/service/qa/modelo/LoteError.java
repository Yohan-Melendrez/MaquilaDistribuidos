/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "lote_errores")
public class LoteError {

    @EmbeddedId
    private LoteErrorId id = new LoteErrorId();

    @ManyToOne
    @MapsId("idLote")
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @ManyToOne
    @MapsId("idError")
    @JoinColumn(name = "id_error")
    private ErrorProduccion error;

    @Column(name = "nivel_atencion")
    @Enumerated(EnumType.STRING)
    private NivelAtencion nivelAtencion;

    public LoteErrorId getId() {
        return id;
    }

    public void setId(LoteErrorId id) {
        this.id = id;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public ErrorProduccion getError() {
        return error;
    }

    public void setError(ErrorProduccion error) {
        this.error = error;
    }

    public NivelAtencion getNivelAtencion() {
        return nivelAtencion;
    }

    public void setNivelAtencion(NivelAtencion nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }
}
