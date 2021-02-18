package payments.businesslogic.interfaces;

import payments.businesslogic.exceptions.InvalidAccount;
import payments.businesslogic.exceptions.InvalidToken;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.models.Payment;

public interface PaymentService {

    Payment createPayment(Payment payment) throws InvalidToken, InvalidAccount, QueueException;

    Payment getPayment(String id);

}
