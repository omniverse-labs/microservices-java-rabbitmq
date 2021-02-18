package messaging.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import messaging.interfaces.EventReceiver;
import messaging.models.Event;

public class RabbitMqListener {

    String hostname;
    EventReceiver eventReceiver;

    public RabbitMqListener(EventReceiver eventReceiver, String hostname) {
        this.hostname = hostname;
        this.eventReceiver = eventReceiver;
    }

    public void listen(String exchangeName, String queueType, String topic) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.hostname);

        // a hook used during testing
        if (this.hostname == "localhost") {
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
        }

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, queueType);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, topic);

        DeliverCallback deliverCallback = (consumerType, delivery) -> {

            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println("Receiving " + message);

            Event event = new Gson().fromJson(message, Event.class);

            try {
                eventReceiver.receiveEvent(event);
            } catch (Exception e) {
                throw new Error(e);
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }
}
