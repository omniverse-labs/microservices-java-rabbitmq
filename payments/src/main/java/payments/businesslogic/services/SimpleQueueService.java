package payments.businesslogic.services;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import messaging.interfaces.EventReceiver;
import messaging.interfaces.EventSender;
import messaging.models.Event;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.interfaces.QueueService;
import payments.businesslogic.models.AccountInfo;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;

public class SimpleQueueService implements EventReceiver, QueueService {

    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String QUEUE_TYPE = "topic";

    private static final String TOKENS_CMD_BASE = "tokens.cmds.";
    private static final String ACCOUNTS_CMD_BASE = "accounts.cmds.";
    private static final String PAYMENTS_EVENT_BASE = "payments.events.";

    private static final String VALIDATE_TOKEN_CMD = "validateToken";
    private static final String VALIDATE_ACCOUNT_CMD = "validateAccount";

    private static final String TOKEN_VALIDATED_EVENT = "tokenValidated";
    private static final String ACCOUNT_VALIDATED_EVENT = "accountValidated";
    private static final String PAYMENT_CREATED_EVENT = "paymentCreated";

    private EventSender eventSender;
    private CompletableFuture<AccountInfo> accountValidationResult;
    private CompletableFuture<TokenInfo> tokenValidationResult;

    public SimpleQueueService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public TokenInfo validateToken(String token) throws QueueException {

        var event = new Event(VALIDATE_TOKEN_CMD, token);

        tokenValidationResult = new CompletableFuture<TokenInfo>();

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, TOKENS_CMD_BASE + VALIDATE_TOKEN_CMD);
        } catch (Exception e) {
            throw new QueueException("error validating token");
        }

        return tokenValidationResult.join();
    }

    @Override
    public AccountInfo validateAccount(String accountId) throws QueueException {

        var event = new Event(VALIDATE_ACCOUNT_CMD, accountId);

        accountValidationResult = new CompletableFuture<AccountInfo>();

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, ACCOUNTS_CMD_BASE + VALIDATE_ACCOUNT_CMD);
        } catch (Exception e) {
            throw new QueueException("error validating account");
        }

        return accountValidationResult.join();
    }

    @Override
    public void publishPaymentCreatedEvent(Payment payment) throws QueueException {

        Event event = new Event(PAYMENT_CREATED_EVENT, payment);

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, PAYMENTS_EVENT_BASE + PAYMENT_CREATED_EVENT);
        } catch (Exception e) {
            throw new QueueException("error publishing payment created event");
        }

    }

    @Override
    public void receiveEvent(Event event) {

        System.out.printf("handling event: " + event);

        if (event.getEventType().equals(TOKEN_VALIDATED_EVENT)) {

            var tokenInfo = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), TokenInfo.class);

            tokenValidationResult.complete(tokenInfo);

        } else if (event.getEventType().equals(ACCOUNT_VALIDATED_EVENT)) {

            var accountInfo = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), AccountInfo.class);

            accountValidationResult.complete(accountInfo);

        } else {

            System.out.print("event ignored: " + event.getEventType());
        }

    }

}
