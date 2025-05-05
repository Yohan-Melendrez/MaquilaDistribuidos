/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.qa.rabbit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
/**
 *
 * @author abelc
 */
@Configuration
public class RabbitMQConfigLotes {
  

    public static final String QUEUE_NAME = "lote.queue";
    public static final String EXCHANGE_NAME = "lote.exchange";
    public static final String ROUTING_KEY = "lote.routingKey";

    @Bean
    public Queue loteQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange loteExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding loteBinding(Queue loteQueue, DirectExchange loteExchange) {
        return BindingBuilder.bind(loteQueue).to(loteExchange).with(ROUTING_KEY);
    }
}
