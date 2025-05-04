package service.inspeccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.Inspeccion;

public interface InspeccionRepositorio extends JpaRepository<Inspeccion,Integer> { }
