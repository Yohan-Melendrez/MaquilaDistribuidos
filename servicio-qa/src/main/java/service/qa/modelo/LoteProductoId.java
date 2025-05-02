    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package service.qa.modelo;

    import jakarta.persistence.Embeddable;
    import java.io.Serializable;
    import java.util.Objects;

    /**
     *
     * @author abelc
     */
    @Embeddable
    public class LoteProductoId implements Serializable {

        private Integer idLote;
        private Integer idProducto;

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

        // Getters y Setters

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
        
    }
