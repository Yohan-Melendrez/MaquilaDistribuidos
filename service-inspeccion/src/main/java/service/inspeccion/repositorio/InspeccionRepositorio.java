package service.inspeccion.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.Inspeccion;

public interface InspeccionRepositorio extends JpaRepository<Inspeccion, Integer> {

    List<Inspeccion> findByLote_IdLoteAndInspector_IdInspector(Integer idLote, Integer idInspector);

}
