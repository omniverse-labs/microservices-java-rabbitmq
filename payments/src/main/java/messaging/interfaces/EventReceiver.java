package messaging.interfaces;

import messaging.models.Event;

public interface EventReceiver {
    void receiveEvent(Event event) throws Exception;
}
