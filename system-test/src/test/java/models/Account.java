package models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Account {

    private String id;
    private String firstName;
    private String lastName;
    private String accountType;

    @JsonbCreator
    public Account(@JsonbProperty("id") String id, @JsonbProperty("firstName") String firstName,
            @JsonbProperty("lastName") String lastName, @JsonbProperty("accountType") String accountType) {
        this.id = id;
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

    public String getAccountType() {
        return this.accountType;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", this.firstName, this.lastName, this.accountType);
    }
}
