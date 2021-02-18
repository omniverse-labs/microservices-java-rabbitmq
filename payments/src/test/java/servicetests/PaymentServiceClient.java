package servicetests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import payments.rest.models.CreatePaymentRequest;
import payments.rest.models.ErrorModel;

public class PaymentServiceClient {
    WebTarget baseUrl;

    public PaymentServiceClient() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8005/");
    }

    public ErrorModel createPayment(String token, String customerId, String merchantId, String description,
            Double amount) {

        var createPaymentRequest = new CreatePaymentRequest(amount, token, merchantId, customerId, description);

        Response response = baseUrl.path("payments").request()
                .post(Entity.entity(createPaymentRequest, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            return null;
        } else {
            var error = response.readEntity(ErrorModel.class);
            return error;
        }
    }
}
