package accounts.repository;

import java.util.ArrayList;
import java.util.List;

import accounts.businesslogic.models.Account;

public class InMemoryAccountsRepository implements AccountsRepository {

    private static List<Account> accounts;

    public InMemoryAccountsRepository() {
        accounts = new ArrayList<Account>();
    }

    @Override
    public void saveAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Account getAccount(String accountId) {
        return accounts.stream().filter(account -> account.getId().equals(accountId)).findFirst().orElse(null);
    }

}
