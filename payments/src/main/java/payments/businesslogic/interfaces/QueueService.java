package payments.businesslogic.interfaces;

import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.models.AccountInfo;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;

public interface QueueService {

    TokenInfo validateToken(String token) throws QueueException;

    AccountInfo validateAccount(String accountId) throws QueueException;

    void publishPaymentCreatedEvent(Payment payment) throws QueueException;
}
