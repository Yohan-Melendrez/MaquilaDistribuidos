package rabbit;

import dto.NotificacionDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Clase responsable de enviar notificaciones a RabbitMQ usando Spring Boot.
 */
@Service
public class ProductorNotificaciones {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    public ProductorNotificaciones(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * Envía un objeto NotificacionDTO a la cola de RabbitMQ.
     *
     * @param dto el objeto NotificacionDTO a enviar
     */
    public void enviarNotificacion(NotificacionDTO dto) {
        amqpTemplate.convertAndSend(queueName, dto);
        System.out.println(" [✔] Notificación enviada a la cola: " + queueName);
    }
}
