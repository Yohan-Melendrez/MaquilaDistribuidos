package service.qa;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class BasicApplication {

    public static void main(String[] args) {
<<<<<<< HEAD
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\ID145\\Documents\\MaquilaDistribuidos\\truststore.jks");
=======
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\USER\\Documents\\ITSON\\Trabajos ITSON\\Sexto Semestre\\Sistemas Distribuidos\\Proyecto\\MaquilaDistribuidos\\truststore.jks");
>>>>>>> reporteV2
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStoreType","JKS");
        SpringApplication.run(BasicApplication.class, args);
        
    }
}
