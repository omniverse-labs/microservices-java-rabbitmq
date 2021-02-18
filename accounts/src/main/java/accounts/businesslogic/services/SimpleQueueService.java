package accounts.businesslogic.services;

import com.google.gson.Gson;

import accounts.businesslogic.exceptions.QueueException;
import accounts.businesslogic.interfaces.QueueService;
import accounts.businesslogic.models.Account;
import accounts.businesslogic.models.AccountInfo;
import accounts.repository.AccountsRepository;
import messaging.interfaces.EventReceiver;
import messaging.interfaces.EventSender;
import messaging.models.Event;

public class SimpleQueueService implements EventReceiver, QueueService {

    private static final String EXCHANGE_NAME = "paymentsExchange";
    private static final String QUEUE_TYPE = "topic";

    private static final String VALIDATE_ACCOUNT_CMD = "validateAccount";
    private static final String ACCOUNT_EVENTS_BASE = "accounts.events.";

    private static final String ACCOUNT_VALIDATED_EVENT = "accountValidated";
    private static final String ACCOUNT_CREATED_EVENT = "accountCreated";

    private EventSender eventSender;
    private AccountsRepository accountsRepository;

    public SimpleQueueService(EventSender eventSender, AccountsRepository accountsRepository) {
        this.eventSender = eventSender;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {

        System.out.printf("handling event: " + event);

        if (event.getEventType().equals(VALIDATE_ACCOUNT_CMD)) {

            var accountId = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            Event e = new Event(ACCOUNT_VALIDATED_EVENT, new AccountInfo(accountId, false));

            if (this.accountsRepository.getAccount(accountId) != null) {
                e = new Event(ACCOUNT_VALIDATED_EVENT, new AccountInfo(accountId, true));
            }

            this.eventSender.sendEvent(e, EXCHANGE_NAME, QUEUE_TYPE, ACCOUNT_EVENTS_BASE + ACCOUNT_VALIDATED_EVENT);

        } else {

            System.out.print("event ignored: " + event.getEventType());
        }

    }

    @Override
    public void PublishAccountCreatedEvent(Account account) throws QueueException {

        Event event = new Event(ACCOUNT_CREATED_EVENT, account);

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, ACCOUNT_EVENTS_BASE + ACCOUNT_CREATED_EVENT);
        } catch (Exception e) {
            throw new QueueException("error publishing account created event");
        }

    }

}
