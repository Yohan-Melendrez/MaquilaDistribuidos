package service.inspeccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.Lote;

public interface LoteRepositorio extends JpaRepository<Lote,Integer> { }
