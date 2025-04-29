/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Gabriel
 */
public class AsignarLoteDTO {

    private Long loteId;
    private Long inspectorId;
    private String nombreInspector;

    public AsignarLoteDTO() {
    }

    public AsignarLoteDTO(Long loteId, Long inspectorId, String nombreInspector) {
        this.loteId = loteId;
        this.inspectorId = inspectorId;
        this.nombreInspector = nombreInspector;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getNombreInspector() {
        return nombreInspector;
    }

    public void setNombreInspector(String nombreInspector) {
        this.nombreInspector = nombreInspector;
    }
}
