package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dto.NotificacionDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

/**
 * Clase responsable de enviar notificaciones a RabbitMQ sin usar Spring.
 */
public class ProductorNotificaciones {

    private static final String QUEUE_NAME = "notificacionesQueue";

    /**
     * Envía un objeto NotificacionDTO a la cola de RabbitMQ.
     *
     * @param dto el objeto NotificacionDTO a enviar
     */
    public void enviarNotificacion(NotificacionDTO dto) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");         // Cambia si RabbitMQ está en otra IP
        factory.setUsername("guest");         // Usuario de RabbitMQ
        factory.setPassword("guest");         // Contraseña de RabbitMQ
        factory.setPort(5672);                // Puerto por defecto de RabbitMQ

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] mensaje = serializar(dto);

            channel.basicPublish("", QUEUE_NAME, null, mensaje);

            System.out.println(" [✔] Notificación enviada a la cola: " + QUEUE_NAME);

        } catch (IOException | TimeoutException e) {
            System.err.println(" [✘] Error al enviar notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convierte el objeto NotificacionDTO a un arreglo de bytes usando serialización estándar Java.
     *
     * @param dto el objeto a serializar
     * @return arreglo de bytes representando el objeto
     * @throws IOException si falla la serialización
     */
    private byte[] serializar(NotificacionDTO dto) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(dto);
            oos.flush();
            return bos.toByteArray();
        }
    }
}
