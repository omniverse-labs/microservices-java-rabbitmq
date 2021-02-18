package payments.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import payments.businesslogic.interfaces.PaymentService;
import payments.rest.PaymentServiceFactory;

@Path("payments/{id}")
public class PaymentResource {

    private PaymentService paymentService;

    public PaymentResource() {
        paymentService = new PaymentServiceFactory().getService();
    }

    @GET
    public Response getPayment(@PathParam("id") String id) {
        var payment = this.paymentService.getPayment(id);
        if (payment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(payment).build();
    }

}
