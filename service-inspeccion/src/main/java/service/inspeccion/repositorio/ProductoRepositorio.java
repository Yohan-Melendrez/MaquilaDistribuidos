package service.inspeccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import service.inspeccion.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {}
