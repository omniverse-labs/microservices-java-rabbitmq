package tokens.repository;

import java.util.ArrayList;
import java.util.List;

import tokens.businesslogic.models.Token;

public class InMemoryTokenRepository implements TokenRepository {

    private static List<Token> savedTokens;

    public InMemoryTokenRepository() {
        savedTokens = new ArrayList<Token>();
    }

    @Override
    public void saveTokens(List<Token> tokens) {
        savedTokens.addAll(tokens);
    }

    @Override
    public Token getToken(String tokenString) {
        return savedTokens.stream().filter(token -> token.getTokenString().equals(tokenString)).findFirst()
                .orElse(null);
    }

}
