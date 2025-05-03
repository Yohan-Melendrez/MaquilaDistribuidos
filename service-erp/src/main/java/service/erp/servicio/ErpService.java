/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.servicio;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.erp.dtos.CrearLoteDTO;
import service.erp.modelo.Lote;
import service.erp.modelo.LoteProducto;
import service.erp.modelo.LoteProductoId;
import service.erp.modelo.Producto;
import service.erp.repositorio.LoteRepositorio;
import service.erp.repositorio.ProductoRepositorio;

/**
 *
 * @author abelc
 */
@Service
public class ErpService {
    
    @Autowired
    private LoteRepositorio loteRepository;

    @Autowired
    private ProductoRepositorio productoRepository;

    @Transactional
    public Lote crearLote(CrearLoteDTO dto) {
        Lote lote = new Lote();
      
        lote.setNombreLote(dto.getNombreLote());
        lote.setEstado("En proceso");

        List<LoteProducto> productosDelLote = dto.getProductos().stream().map(item -> {
            Producto producto = productoRepository.findById(item.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getIdProducto()));

            LoteProducto lp = new LoteProducto();
            lp.setProducto(producto);
            lp.setLote(lote);
            lp.setCantidad(item.getCantidad());

            LoteProductoId id = new LoteProductoId();
            id.setIdLote(lote.getIdLote());
            id.setIdProducto(producto.getIdProducto());
            lp.setId(id);

            return lp;
        }).collect(Collectors.toList());

        lote.setProductos(productosDelLote);
        return loteRepository.save(lote);
    }

    public List<Lote> obtenerTodosLotes() {
        return loteRepository.findAll();
    }

    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

   
}
