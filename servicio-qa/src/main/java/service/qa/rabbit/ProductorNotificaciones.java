package service.qa.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import service.qa.dto.NotificacionDTO;

@Service
public class ProductorNotificaciones {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    public ProductorNotificaciones(@Lazy RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarNotificacion(NotificacionDTO dto) {
        rabbitTemplate.convertAndSend(queueName, dto);
        System.out.println("✅ Notificación enviada a la cola: " + queueName);
    }
}
