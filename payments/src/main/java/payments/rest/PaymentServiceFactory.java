package payments.rest;

import messaging.interfaces.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import payments.businesslogic.interfaces.PaymentService;
import payments.businesslogic.services.SimplePaymentService;
import payments.businesslogic.services.SimpleQueueService;
import payments.repository.InMemoryPaymentRepository;
import payments.repository.PaymentRepository;

public class PaymentServiceFactory {

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String TOKENS_EVENTS = "tokens.events.*";
    private static final String ACCOUNTS_EVENTS = "accounts.events.*";

    static PaymentService paymentService = null;

    public PaymentService getService() {
        if (paymentService != null) {
            return paymentService;
        }

        EventSender eventSender = new RabbitMqSender("rabbitMq");
        SimpleQueueService queueService = new SimpleQueueService(eventSender);
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();

        paymentService = new SimplePaymentService(paymentRepository, queueService);

        RabbitMqListener rabbitMqListener = new RabbitMqListener(queueService, "rabbitMq");
        try {
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, TOKENS_EVENTS);
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, ACCOUNTS_EVENTS);
        } catch (Exception e) {
            throw new Error(e);
        }

        return paymentService;
    }
}
