package tokens.businesslogic.interfaces;

import java.util.List;

import tokens.businesslogic.exceptions.QueueException;
import tokens.businesslogic.models.Token;

public interface QueueService {
    void PublishTokensCreatedEvent(List<Token> tokens) throws QueueException;
}
