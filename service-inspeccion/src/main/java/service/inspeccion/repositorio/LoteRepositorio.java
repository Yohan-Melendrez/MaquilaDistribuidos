package service.inspeccion.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import service.inspeccion.modelo.Lote;

@Repository
public interface LoteRepositorio extends JpaRepository<Lote, Integer> {
    List<Lote> findByInspector_IdInspector(Integer idInspector);
}

