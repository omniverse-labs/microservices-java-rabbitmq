package clients;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Token;

public class TokenServiceClient {

    Client client;
    WebTarget baseUrl;

    public TokenServiceClient() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8020");
    }

    public List<Token> createTokens(int count) {

        JsonObject json = Json.createObjectBuilder().add("count", count).build();

        Response response = baseUrl.path("tokens").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {

            List<Token> tokens = response.readEntity(new GenericType<List<Token>>() {
            });

            for (Token token : tokens) {
                System.out.println(token.getTokenString());
            }

            return tokens;
        }

        return null;
    }

}
