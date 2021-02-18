package payments.businesslogic.exceptions;

public class InvalidToken extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidToken(String message) {
        super(message);
    }

}
