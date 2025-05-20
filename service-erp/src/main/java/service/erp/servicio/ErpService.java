/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.erp.dtos.CrearLoteDTO;
import service.erp.dtos.ErrorDTO;
import service.erp.dtos.LoteItemDTO;
import service.erp.dtos.ProductoDTO;
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
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LoteRepositorio loteRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductoRepositorio productoRepository;
    private final RestTemplate restTemplate = new RestTemplate(); // o inyectable

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

    public List<ProductoDTO> obtenerTodosProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    public void enviarLoteAQa(Integer idLote) {
        Lote lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado: " + idLote));

        CrearLoteDTO dto = new CrearLoteDTO();
        dto.setIdLote(lote.getIdLote());
        dto.setNombreLote(lote.getNombreLote());
        dto.setEstado(lote.getEstado());

        List<LoteItemDTO> productosDTO = new ArrayList<>();
        for (LoteProducto lp : lote.getProductos()) {
            Producto p = lp.getProducto();

            LoteItemDTO itemDTO = new LoteItemDTO();
            itemDTO.setIdProducto(p.getIdProducto());
            itemDTO.setNombre(p.getNombre());
            itemDTO.setCantidad(lp.getCantidad());

            List<ErrorDTO> errores = p.getErrores().stream().map(error -> {
                ErrorDTO err = new ErrorDTO();
                err.setIdError(error.getIdError());
                err.setNombre(error.getNombre());
                err.setDescripcion(error.getDescripcion());
                err.setCosto(error.getCosto_usd());
                return err;
            }).collect(Collectors.toList());

            itemDTO.setErrores(errores);
            productosDTO.add(itemDTO);
        }

        dto.setProductos(productosDTO);

        try {
            String url = "https://localhost:8082/qa/recibirLote"; // QA app URL

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CrearLoteDTO> request = new HttpEntity<>(dto, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

            System.out.println("✅ Lote enviado a QA por REST: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error enviando lote a QA por REST", e);
        }
    }
}
