/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rabbit;

import dto.NotificacionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriel
 */
@Component
public class ProductorNotificaciones {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductorNotificaciones(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarNotificacion(NotificacionDTO dto) {
        rabbitTemplate.convertAndSend("notificacionesQueue", dto);
    }
}
