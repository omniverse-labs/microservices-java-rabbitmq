package clients;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Payment;

public class PaymentServiceClient {

    Client client;
    WebTarget baseUrl;

    public PaymentServiceClient() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8010");
    }

    public Payment createPayment(String customerId, String merchantId, String token, String description,
            Double amount) {

        JsonObject json = Json.createObjectBuilder().add("customerId", customerId).add("merchantId", merchantId)
                .add("token", token).add("description", description).add("amount", amount).build();

        Response response = baseUrl.path("payments").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            var resourceLocation = response.getHeaderString("location");

            System.out.println(resourceLocation);

            var resourceUrl = client.target(resourceLocation);

            Payment payment = resourceUrl.request().get(Payment.class);

            System.out.println(payment.toString());

            return payment;
        }

        return null;
    }

}
