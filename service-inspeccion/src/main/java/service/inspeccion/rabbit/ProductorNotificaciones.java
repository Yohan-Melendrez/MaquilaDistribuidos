package service.inspeccion.rabbit;

import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;

import service.inspeccion.dtos.NotificacionDTO;

@Service
public class ProductorNotificaciones {
    
    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    public ProductorNotificaciones(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }


    public void enviarNotificacion(NotificacionDTO dto){
        amqpTemplate.convertAndSend(queueName, dto);
        System.out.println("Notificación enviada a la cola: " + dto.getTitulo() + " - " + dto.getMensaje());
    }
}
