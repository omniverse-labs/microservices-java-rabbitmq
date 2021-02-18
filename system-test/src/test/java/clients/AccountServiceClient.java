package clients;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Account;

public class AccountServiceClient {

    Client client;
    WebTarget baseUrl;

    public AccountServiceClient() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8030");
    }

    public Account createAccount(String firstName, String lastName, String accountType) {

        JsonObject json = Json.createObjectBuilder().add("firstName", firstName).add("lastName", lastName)
                .add("accountType", accountType).build();

        Response response = baseUrl.path("accounts").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            var resourceLocation = response.getHeaderString("location");

            System.out.println(resourceLocation);

            var resourceUrl = client.target(resourceLocation);

            Account account = resourceUrl.request().get(Account.class);

            System.out.println(account.toString());

            return account;
        }

        return null;
    }
}
