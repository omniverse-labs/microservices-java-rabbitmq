package tokens.businesslogic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tokens.businesslogic.exceptions.QueueException;
import tokens.businesslogic.interfaces.QueueService;
import tokens.businesslogic.interfaces.TokenService;
import tokens.businesslogic.models.Token;
import tokens.repository.TokenRepository;

public class SimpleTokenService implements TokenService {

    private QueueService queueService;
    private TokenRepository tokenRepository;

    public SimpleTokenService(TokenRepository tokenRepository, QueueService queueService) {
        this.queueService = queueService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<Token> createTokens(int count) throws QueueException {

        // 1. create tokens
        var tokens = new ArrayList<Token>();
        for (int i = 0; i < count; i++) {
            tokens.add(new Token(UUID.randomUUID().toString()));
        }

        // 2. store tokens
        this.tokenRepository.saveTokens(tokens);

        // 3. publish tokens created event
        this.queueService.PublishTokensCreatedEvent(tokens);

        return tokens;
    }

}
