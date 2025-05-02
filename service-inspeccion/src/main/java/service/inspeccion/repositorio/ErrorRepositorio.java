package service.inspeccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.Error;

public interface ErrorRepositorio extends JpaRepository<Error, Integer> {}
