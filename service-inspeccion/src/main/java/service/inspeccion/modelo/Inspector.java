/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.inspeccion.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "inspectores")
public class Inspector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInspector;

    private String nombre;

    private Boolean activo = true;  // Si está disponible para asignación

    @OneToMany(mappedBy = "inspector")
    @JsonManagedReference
    private List<Lote> lotes = new ArrayList<>();

    public Integer getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(Integer idInspector) {
        this.idInspector = idInspector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }
    
    
}
