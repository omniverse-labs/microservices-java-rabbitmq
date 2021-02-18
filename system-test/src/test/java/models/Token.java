package models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Token {
    private String tokenString;

    @JsonbCreator
    public Token(@JsonbProperty("tokenString") String tokenString) {
        this.tokenString = tokenString;
    }

    public String getTokenString() {
        return this.tokenString;
    }
}
