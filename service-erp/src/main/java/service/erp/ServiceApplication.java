package service.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\USER\\Documents\\ITSON\\Trabajos ITSON\\Sexto Semestre\\Sistemas Distribuidos\\Proyecto\\MaquilaDistribuidos\\truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        SpringApplication.run(ServiceApplication.class, args);
    }

}
