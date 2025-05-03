package service.inspeccion.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import service.inspeccion.dtos.AsignarLoteDTO;
import service.inspeccion.dtos.ProductoDelLoteDTO;
import service.inspeccion.dtos.RegistroInspeccionDTO;
import service.inspeccion.modelo.*;
import service.inspeccion.modelo.Error;
import service.inspeccion.repositorio.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspeccionService {

    @Autowired
    private InspeccionRepositorio inspeccionRepo;

    @Autowired
    private LoteRepositorio loteRepo;

    @Autowired
    private LoteInspectorRepositorio loteInspectorRepo;

    @Autowired
    private ProductoRepositorio productoRepo;

    @Autowired
    private ErrorRepositorio errorRepo;

    @Autowired
    private LoteProductoRepositorio loteProductoRepo;



    public void registrarInspeccion(RegistroInspeccionDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Producto producto = productoRepo.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        for (Integer idError : dto.getErroresSeleccionados()) {
            Error error = errorRepo.findById(idError)
                    .orElseThrow(() -> new RuntimeException("Error no encontrado"));

            Inspeccion inspeccion = new Inspeccion();
            inspeccion.setLote(lote);
            inspeccion.setProducto(producto);
            inspeccion.setError(error);
            inspeccion.setInspector(dto.getInspector());

            inspeccionRepo.save(inspeccion);
        }
    }

    public void asignarLoteAInspector(AsignarLoteDTO dto) {
        Lote lote = loteRepo.findById(dto.getIdLote())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        boolean yaAsignado = loteInspectorRepo.existsByLote_IdLoteAndInspector(dto.getIdLote(), dto.getInspector());
        if (yaAsignado) {
            // 👇 Aquí usamos CONFLICT (409) en vez de lanzar RuntimeException
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este lote ya ha sido asignado al inspector.");
        }

        LoteInspector asignacion = new LoteInspector();
        asignacion.setLote(lote);
        asignacion.setInspector(dto.getInspector());

        loteInspectorRepo.save(asignacion);
    }

    public List<Lote> obtenerLotesPorInspector(String inspector) {
        List<LoteInspector> asignaciones = loteInspectorRepo.findByInspector(inspector);
        return asignaciones.stream()
                .map(LoteInspector::getLote)
                .collect(Collectors.toList());
    }

    public List<ProductoDelLoteDTO> obtenerProductosDeLote(Integer idLote) {
        List<LoteProducto> relaciones = loteProductoRepo.findByIdLote(idLote);

        return relaciones.stream().map(rel -> {
            Producto p = rel.getProducto();
            return new ProductoDelLoteDTO(
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getDescripcion(),
                    rel.getCantidad());
        }).collect(Collectors.toList());
    }

    public List<Error> obtenerTodosLosErrores() {
        return errorRepo.findAll();
    }
    
    public List<Error> obtenerErroresPorProducto(Integer idProducto) {
        return errorRepo.findErroresPorProducto(idProducto);
    }
    


}
