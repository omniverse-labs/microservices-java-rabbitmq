package servicetests;

import com.google.gson.Gson;

import messaging.interfaces.EventReceiver;
import messaging.interfaces.EventSender;
import messaging.models.Event;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import payments.businesslogic.models.AccountInfo;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;

public class TestQueueClient implements EventReceiver {

    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String QUEUE_TYPE = "topic";

    private static final String TOKENS_CMD_BASE = "tokens.cmds.";
    private static final String ACCOUNTS_CMD_BASE = "accounts.cmds.";

    private static final String PAYMENTS_EVENT_BASE = "payments.events.";
    private static final String TOKENS_EVENT_BASE = "tokens.events.";
    private static final String ACCOUNTS_EVENT_BASE = "accounts.events.";

    private static final String VALIDATE_TOKEN_CMD = "validateToken";
    private static final String VALIDATE_ACCOUNT_CMD = "validateAccount";

    private static final String TOKEN_VALIDATED_EVENT = "tokenValidated";
    private static final String ACCOUNT_VALIDATED_EVENT = "accountValidated";
    private static final String PAYMENT_CREATED_EVENT = "paymentCreated";

    private EventSender sender;

    public TestQueueClient() {
        this.sender = new RabbitMqSender("localhost");

        RabbitMqListener rabbitMqListener = new RabbitMqListener(this, "localhost");
        try {
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, ACCOUNTS_CMD_BASE + "*");
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, TOKENS_CMD_BASE + "*");
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, PAYMENTS_EVENT_BASE + "*");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void receiveEvent(Event event) throws Exception {

        if (event.getEventType().equals(VALIDATE_TOKEN_CMD)) {

            var token = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            var response = new Event(TOKEN_VALIDATED_EVENT, new TokenInfo(token, false));

            // one valid token for testing
            if (token.equals("test_valid_token")) {
                response = new Event(TOKEN_VALIDATED_EVENT, new TokenInfo(token, true));
            }

            sender.sendEvent(response, EXCHANGE_NAME, QUEUE_TYPE, TOKENS_EVENT_BASE + TOKEN_VALIDATED_EVENT);

        } else if (event.getEventType().equals(VALIDATE_ACCOUNT_CMD)) {

            var accountId = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            var response = new Event(ACCOUNT_VALIDATED_EVENT, new AccountInfo(accountId, false));

            if (accountId.equals("test_valid_customer_id")) {
                response = new Event(ACCOUNT_VALIDATED_EVENT, new AccountInfo(accountId, true));
            }

            if (accountId.equals("test_valid_merchant_id")) {
                response = new Event(ACCOUNT_VALIDATED_EVENT, new AccountInfo(accountId, true));
            }

            sender.sendEvent(response, EXCHANGE_NAME, QUEUE_TYPE, ACCOUNTS_EVENT_BASE + ACCOUNT_VALIDATED_EVENT);

        } else if (event.getEventType().equals(PAYMENT_CREATED_EVENT)) {

            var payment = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Payment.class);

            System.out.println(payment.getId() + ", " + payment.getToken());

        } else {

            System.out.print("Event ignored" + event);

        }

    }

}
