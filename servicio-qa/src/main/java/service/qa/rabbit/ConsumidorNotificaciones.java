/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import service.qa.dto.NotificacionDTO;
import service.qa.servicio.ServicioQA;

/**
 *
 * @author Gabriel
 */
@Component
public class ConsumidorNotificaciones {

    private final ServicioQA servicioQA;

    public ConsumidorNotificaciones(ServicioQA servicioQA) {
        this.servicioQA = servicioQA;
    }

    @RabbitListener(queues = "${rabbitmq.queue.notificaciones}")
    public void recibirNotificacion(NotificacionDTO dto) {
        System.out.println("📨 Notificación recibida: " + dto.getTitulo());
        servicioQA.Notificaciones(dto);
    }
}
