/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.eventos;

/**
 *
 * @author abelc
 */
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.erp.config.RabbitConfig;

@Component
public class LoteEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarLote(Object loteDTO) {
        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME,
            RabbitConfig.ROUTING_KEY,
            loteDTO
        );
    }
}
