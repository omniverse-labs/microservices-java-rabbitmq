package tokens.businesslogic.interfaces;

import java.util.List;

import tokens.businesslogic.exceptions.QueueException;
import tokens.businesslogic.models.Token;

public interface TokenService {

    List<Token> createTokens(int count) throws QueueException;
}
