package service.inspeccion.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import service.inspeccion.modelo.ErrorProduccion;

public interface ErrorRepositorio extends JpaRepository<ErrorProduccion,Integer> {
    @Query("SELECT e FROM ErrorProduccion e JOIN e.productos p WHERE p.idProducto = :id")
    List<ErrorProduccion> findErroresPorProducto(@Param("id") Integer idProducto);
}
