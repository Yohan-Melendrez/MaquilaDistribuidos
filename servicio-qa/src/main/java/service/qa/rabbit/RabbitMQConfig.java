/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.rabbit;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel
 */
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.notificaciones}")
    private String nombreCola;

    @Bean
    public Queue notificacionesQueue() {
        return new Queue(nombreCola, true); 
    }

     @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); 

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(mapper);

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> idMappings = new HashMap<>();
        idMappings.put("service.inspeccion.dtos.NotificacionDTO", service.qa.dto.NotificacionDTO.class);
        typeMapper.setIdClassMapping(idMappings);
        converter.setJavaTypeMapper(typeMapper);

        return converter;
    }
}
