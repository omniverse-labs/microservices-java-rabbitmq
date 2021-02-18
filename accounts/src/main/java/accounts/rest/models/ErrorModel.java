package accounts.rest.models;

public class ErrorModel {
    private String message;

    public ErrorModel() {

    }

    public ErrorModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
