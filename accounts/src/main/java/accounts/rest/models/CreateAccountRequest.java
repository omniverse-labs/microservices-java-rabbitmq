package accounts.rest.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.ValidationException;

public class CreateAccountRequest {

    private String firstName;
    private String lastName;
    private String accountType;

    public CreateAccountRequest() {
        super();
    }

    @JsonbCreator
    public CreateAccountRequest(@JsonbProperty("firstName") String firstName,
            @JsonbProperty("lastName") String lastName, @JsonbProperty("accountType") String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
    }

    public void validate() throws ValidationException {
        if (this.firstName == null || this.firstName.trim().isEmpty()) {
            throw new ValidationException("first name is required");
        }

        if (this.lastName == null || this.lastName.trim().isEmpty()) {
            throw new ValidationException("last name is required");
        }

        if (this.accountType == null || this.accountType.trim().isEmpty()) {
            throw new ValidationException("account type is required");
        }

        if (!this.accountType.equals("customer") && !this.accountType.equals("merchant")) {
            throw new ValidationException("account type should be either customer or merchant");
        }
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
}
