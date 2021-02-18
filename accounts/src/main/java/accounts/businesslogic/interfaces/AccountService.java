package accounts.businesslogic.interfaces;

import accounts.businesslogic.exceptions.QueueException;
import accounts.businesslogic.models.Account;

public interface AccountService {
    Account createAccount(Account account) throws QueueException;

    Account getAccount(String id);
}
