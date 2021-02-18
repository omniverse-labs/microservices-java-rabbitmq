package messaging.interfaces;

import messaging.models.Event;

public interface EventSender {
    void sendEvent(Event event, String exchangeName, String queueType, String topic) throws Exception;
}
