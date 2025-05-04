package service.inspeccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.inspeccion.modelo.Inspector;
import java.util.Optional;


@Repository
public interface InspectorRepositorio extends JpaRepository<Inspector,Integer> {
    Optional<Inspector> findByNombre(String nombre);
}
