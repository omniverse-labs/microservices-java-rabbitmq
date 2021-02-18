package tokens.rest;

import messaging.interfaces.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import tokens.businesslogic.interfaces.TokenService;
import tokens.businesslogic.services.SimpleQueueService;
import tokens.businesslogic.services.SimpleTokenService;
import tokens.repository.InMemoryTokenRepository;
import tokens.repository.TokenRepository;

public class TokenServiceFactory {

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String TOKENS_CMDS = "tokens.cmds.*";

    static TokenService tokenService = null;

    public TokenService getService() {

        if (tokenService != null) {
            return tokenService;
        }

        EventSender eventSender = new RabbitMqSender("rabbitMq");
        TokenRepository tokenRepository = new InMemoryTokenRepository();
        SimpleQueueService queueService = new SimpleQueueService(eventSender, tokenRepository);

        tokenService = new SimpleTokenService(tokenRepository, queueService);

        RabbitMqListener rabbitMqListener = new RabbitMqListener(queueService, "rabbitMq");
        try {
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, TOKENS_CMDS);
        } catch (Exception e) {
            throw new Error(e);
        }

        return tokenService;
    }
}
