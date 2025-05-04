package service.inspeccion.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.LoteProducto;
import service.inspeccion.modelo.LoteProductoId;

public interface LoteProductoRepositorio 
    extends JpaRepository<LoteProducto, LoteProductoId> {

    // recorre lote → idLote
    List<LoteProducto> findByLote_IdLote(Integer idLote);
}

