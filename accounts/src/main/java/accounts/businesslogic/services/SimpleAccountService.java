package accounts.businesslogic.services;

import accounts.businesslogic.exceptions.QueueException;
import accounts.businesslogic.interfaces.AccountService;
import accounts.businesslogic.interfaces.QueueService;
import accounts.businesslogic.models.Account;
import accounts.repository.AccountsRepository;

public class SimpleAccountService implements AccountService {

    private QueueService queueService;
    private AccountsRepository accountsRepository;

    public SimpleAccountService(AccountsRepository accountsRepository, QueueService queueService) {
        this.queueService = queueService;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Account createAccount(Account account) throws QueueException {

        // 1. save account in the store
        this.accountsRepository.saveAccount(account);

        // 2. publish account created event
        this.queueService.PublishAccountCreatedEvent(account);

        return account;
    }

    @Override
    public Account getAccount(String id) {
        return this.accountsRepository.getAccount(id);
    }

}
