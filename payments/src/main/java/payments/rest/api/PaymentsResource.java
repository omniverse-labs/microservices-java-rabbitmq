package payments.rest.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import payments.businesslogic.exceptions.InvalidAccount;
import payments.businesslogic.exceptions.InvalidToken;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.interfaces.PaymentService;
import payments.businesslogic.models.Payment;
import payments.rest.PaymentServiceFactory;
import payments.rest.models.CreatePaymentRequest;
import payments.rest.models.ErrorModel;

@Path("/payments")
public class PaymentsResource {

    private PaymentService paymentService;

    public PaymentsResource() {
        paymentService = new PaymentServiceFactory().getService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(CreatePaymentRequest createPaymentRequest) throws URISyntaxException {

        try {
            createPaymentRequest.validate();

            var payment = new Payment(createPaymentRequest.getToken(), createPaymentRequest.getMerchantId(),
                    createPaymentRequest.getCustomerId(), createPaymentRequest.getDescription(),
                    createPaymentRequest.getAmount());

            var createdPayment = paymentService.createPayment(payment);

            return Response.created(new URI(String.format("payments/%s", createdPayment.getId()))).build();

        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (InvalidAccount e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (InvalidToken e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (QueueException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel(e.getMessage()))
                    .build();
        }

    }

}
