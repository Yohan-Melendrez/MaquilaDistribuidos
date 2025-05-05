/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.qa.dto.LotesDTO;
import service.qa.servicio.ServicioQA;

/**
 *
 * @author abelc
 */
@Service
public class LoteListener {

 @Autowired
    private ServicioQA servicioQA;

    @RabbitListener(queues = RabbitMQConfigLotes.QUEUE_NAME)
    public void recibirLote(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LotesDTO lote = objectMapper.readValue(json, LotesDTO.class);
            servicioQA.recibirLoteDesdeERP(lote);
            System.out.println("✅ Lote recibido y procesado: " + lote.getNombreLote());
        } catch (Exception e) {
            System.err.println("❌ Error procesando lote: " + e.getMessage());
        }
    }
}