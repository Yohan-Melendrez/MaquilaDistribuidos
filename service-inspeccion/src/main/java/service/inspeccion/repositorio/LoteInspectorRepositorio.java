package service.inspeccion.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.LoteInspector;

public interface LoteInspectorRepositorio extends JpaRepository<LoteInspector, Integer> {
    List<LoteInspector> findByInspector(String inspector);
    boolean existsByLote_IdLoteAndInspector(Integer idLote, String inspector);


}

