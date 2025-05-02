package service.inspeccion.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import service.inspeccion.modelo.Error;

public interface ErrorRepositorio extends JpaRepository<Error, Integer> {
    @Query(value = "SELECT e.* FROM errores e JOIN producto_errores pe ON e.id_error = pe.id_error WHERE pe.id_producto = :idProducto", nativeQuery = true)
    List<Error> findErroresPorProducto(@Param("idProducto") Integer idProducto);
    
    

}
