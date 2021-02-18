package payments.businesslogic.models;

public class AccountInfo {

    private String accountId;
    private Boolean isValid;

    public AccountInfo(String accountId, Boolean isValid) {
        this.accountId = accountId;
        this.isValid = isValid;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

}
