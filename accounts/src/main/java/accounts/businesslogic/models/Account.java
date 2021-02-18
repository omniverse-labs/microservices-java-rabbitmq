package accounts.businesslogic.models;

import java.util.UUID;

public class Account {

    private String id;
    private String firstName;
    private String lastName;
    private AccountType accountType;

    public Account(String firstName, String lastName, AccountType accountType) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }
}
