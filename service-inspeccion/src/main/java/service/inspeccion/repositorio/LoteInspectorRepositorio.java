package service.inspeccion.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.inspeccion.modelo.LoteInspector;

@Repository
public interface LoteInspectorRepositorio 
        extends JpaRepository<LoteInspector, Integer> {

    boolean existsByLote_IdLoteAndInspector(Integer idLote, String inspector);

    List<LoteInspector> findByInspector(String inspector);
}
