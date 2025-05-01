/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dto.LotesDTO;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import modelo.Lotes;
import java.util.stream.Collectors;
import repositorio.LoteRepositorio;

/**
 *
 * @author Gabriel
 */
public class ServicioErp {

    private LoteRepositorio loteRepositorio;

    /**
     * Crea un nuevo lotee en el sistema
     */
    @Transactional
    public LotesDTO crearLote(LotesDTO loteDTO) {
        Lotes lote = new Lotes(
                loteDTO.getCodigoLote(),
                loteDTO.getProducto(),
                loteDTO.getCantidadTotal()
        );

        lote.setFechaCreacion(LocalDateTime.now());
        lote = loteRepositorio.save(lote);

        // Notificar al sistema ERP que se ha creado un lotee
        return convertToDTO(lote);
    }



    /**
     * Convierte entidad Lot a DTO
     */
    private LotesDTO convertToDTO(Lotes lote) {
        return new LotesDTO(
                lote.getId(),
                lote.getCodigoLote(),
                lote.getProducto(),
                lote.getCantidadTotal(),
                lote.getFechaCreacion(),
                lote.getInspectorAsignadoId()
        );
    }
}
