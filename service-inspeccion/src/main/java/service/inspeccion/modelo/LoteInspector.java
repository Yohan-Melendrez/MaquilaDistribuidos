package service.inspeccion.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote_inspector")
public class LoteInspector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación ManyToOne con Lote
    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    // Nombre del inspector (tal como lo recibes en tu DTO)
    @Column(name = "inspector", nullable = false)
    private String inspector;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion = LocalDateTime.now();

    // --- getters y setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }

    public String getInspector() { return inspector; }
    public void setInspector(String inspector) { this.inspector = inspector; }

    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { 
        this.fechaAsignacion = fechaAsignacion; 
    }
}
