package service.inspeccion.rabbit;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import service.inspeccion.dtos.NotificacionDTO;

@Service
public class ProductorNotificaciones {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Autowired
    public ProductorNotificaciones(RabbitTemplate rabbitTemplate, Jackson2JsonMessageConverter converter) {
        rabbitTemplate.setMessageConverter(converter);
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarNotificacion(NotificacionDTO dto) {
        rabbitTemplate.convertAndSend(queueName, dto);
        System.out.println("✅ Notificación enviada a la cola: " + dto.getTitulo() + " - " + dto.getMensaje());
    }
}
