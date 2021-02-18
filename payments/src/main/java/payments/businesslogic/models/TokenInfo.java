package payments.businesslogic.models;

public class TokenInfo {

    private String token;
    private Boolean isValid;

    public TokenInfo(String token, Boolean isValid) {
        this.token = token;
        this.isValid = isValid;
    }

    public String getToken() {
        return this.token;
    }

    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

}
