package service.inspeccion.modelo;

import java.io.Serializable;
import java.util.Objects;

public class LoteProductoId implements Serializable {
    private Integer idLote;
    private Integer idProducto;

    public LoteProductoId() {}

    public LoteProductoId(Integer idLote, Integer idProducto) {
        this.idLote = idLote;
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoteProductoId)) return false;
        LoteProductoId that = (LoteProductoId) o;
        return Objects.equals(idLote, that.idLote) &&
               Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, idProducto);
    }
}
