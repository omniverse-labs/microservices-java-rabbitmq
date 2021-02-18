package payments.businesslogic.exceptions;

public class InvalidAccount extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidAccount(String message) {
        super(message);
    }
}
