/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.config;

import java.util.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author abelc
 */


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "lote.queue";
    public static final String EXCHANGE_NAME = "lote.exchange";
    public static final String ROUTING_KEY = "lote.routingKey";


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Si también quieres declarar la cola (no obligatorio si solo produces):
    @Bean
    public org.springframework.amqp.core.Queue queue() {
        return new org.springframework.amqp.core.Queue(QUEUE_NAME, true); // durable
    }

    @Bean
    public Binding binding(org.springframework.amqp.core.Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
