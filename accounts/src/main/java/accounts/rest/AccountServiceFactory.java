package accounts.rest;

import accounts.businesslogic.interfaces.AccountService;
import accounts.businesslogic.services.SimpleAccountService;
import accounts.businesslogic.services.SimpleQueueService;
import accounts.repository.AccountsRepository;
import accounts.repository.InMemoryAccountsRepository;
import messaging.interfaces.EventSender;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;

public class AccountServiceFactory {

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String ACCOUNTS_CMDS = "accounts.cmds.*";

    static AccountService accountService = null;

    public AccountService getService() {

        if (accountService != null) {
            return accountService;
        }

        EventSender eventSender = new RabbitMqSender("rabbitMq");
        AccountsRepository accountsRepository = new InMemoryAccountsRepository();
        SimpleQueueService queueService = new SimpleQueueService(eventSender, accountsRepository);

        accountService = new SimpleAccountService(accountsRepository, queueService);

        RabbitMqListener rabbitMqListener = new RabbitMqListener(queueService, "rabbitMq");
        try {
            rabbitMqListener.listen(EXCHANGE_NAME, QUEUE_TYPE, ACCOUNTS_CMDS);
        } catch (Exception e) {
            throw new Error(e);
        }

        return accountService;
    }
}
