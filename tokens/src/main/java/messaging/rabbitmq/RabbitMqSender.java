package messaging.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import messaging.interfaces.EventSender;
import messaging.models.Event;

public class RabbitMqSender implements EventSender {

    private String hostname;

    public RabbitMqSender(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void sendEvent(Event event, String exchangeName, String queueType, String topic) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.hostname);

        // a hook used during testing
        if (this.hostname == "localhost") {
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
        }

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, queueType);

            String message = new Gson().toJson(event);

            System.out.println("Sending " + message);

            channel.basicPublish(exchangeName, topic, null, message.getBytes("UTF-8"));
        }
    }

}
