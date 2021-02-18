package tokens.repository;

import java.util.List;

import tokens.businesslogic.models.Token;

public interface TokenRepository {

    void saveTokens(List<Token> tokens);

    Token getToken(String tokenString);
}
