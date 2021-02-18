package tokens.businesslogic.services;

import java.util.List;

import com.google.gson.Gson;

import messaging.interfaces.EventReceiver;
import messaging.interfaces.EventSender;
import messaging.models.Event;
import tokens.businesslogic.exceptions.QueueException;
import tokens.businesslogic.interfaces.QueueService;
import tokens.businesslogic.models.Token;
import tokens.businesslogic.models.TokenInfo;
import tokens.repository.TokenRepository;

public class SimpleQueueService implements EventReceiver, QueueService {

    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String QUEUE_TYPE = "topic";

    private static final String VALIDATE_TOKEN_CMD = "validateToken";
    private static final String TOKEN_VALIDATED_EVENT = "tokenValidated";

    private static final String TOKEN_EVENTS_BASE = "tokens.events.";
    private static final String TOKENS_CREATED_EVENT = "tokensCreated";

    private EventSender eventSender;
    private TokenRepository tokenRepository;

    public SimpleQueueService(EventSender eventSender, TokenRepository tokenRepository) {
        this.eventSender = eventSender;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {

        System.out.printf("handling event: " + event);

        if (event.getEventType().equals(VALIDATE_TOKEN_CMD)) {

            var token = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            Event e = new Event(TOKEN_VALIDATED_EVENT, new TokenInfo(token, false));

            if (this.tokenRepository.getToken(token) != null) {
                e = new Event(TOKEN_VALIDATED_EVENT, new TokenInfo(token, true));
            }

            this.eventSender.sendEvent(e, EXCHANGE_NAME, QUEUE_TYPE, TOKEN_EVENTS_BASE + TOKEN_VALIDATED_EVENT);

        } else {

            System.out.print("event ignored: " + event.getEventType());
        }

    }

    @Override
    public void PublishTokensCreatedEvent(List<Token> tokens) throws QueueException {
        Event event = new Event(TOKENS_CREATED_EVENT, tokens);

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, TOKEN_EVENTS_BASE + TOKENS_CREATED_EVENT);
        } catch (Exception e) {
            throw new QueueException("error publishing tokens created event");
        }

    }

}
