package accounts.businesslogic.interfaces;

import accounts.businesslogic.exceptions.QueueException;
import accounts.businesslogic.models.Account;

public interface QueueService {
    void PublishAccountCreatedEvent(Account account) throws QueueException;
}
