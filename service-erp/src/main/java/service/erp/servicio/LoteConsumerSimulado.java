/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.erp.servicio;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 *
 * @author abelc
 */
public class LoteConsumerSimulado {
       @RabbitListener(queues = "cola_lotes") 
    public void recibirLote(String mensajeJson) {
        System.out.println("📥 Lote recibido en ERP (simulando QA):");
        System.out.println(mensajeJson);
    }
}
