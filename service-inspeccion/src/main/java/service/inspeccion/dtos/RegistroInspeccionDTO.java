package service.inspeccion.dtos;

import java.util.List;

public class RegistroInspeccionDTO {
    private Integer idLote;
    private Integer idProducto;
    private String inspector;
    private List<Integer> erroresSeleccionados;
    private String detalleError ;

    // Getters y setters

    public String getDetalleError() {
        return detalleError;
    }

    public void setDetalleError(String detalleError) {
        this.detalleError = detalleError;
    }
    
    
    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public List<Integer> getErroresSeleccionados() {
        return erroresSeleccionados;
    }

    public void setErroresSeleccionados(List<Integer> erroresSeleccionados) {
        this.erroresSeleccionados = erroresSeleccionados;
    }
}

