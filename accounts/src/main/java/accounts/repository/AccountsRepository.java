package accounts.repository;

import accounts.businesslogic.models.Account;

public interface AccountsRepository {
    void saveAccount(Account account);

    Account getAccount(String accountId);
}
