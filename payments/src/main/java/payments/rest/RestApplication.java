package payments.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import payments.businesslogic.interfaces.PaymentService;

@ApplicationPath("/")
public class RestApplication extends Application {

    private PaymentService paymentService;

    public RestApplication() {
        this.paymentService = new PaymentServiceFactory().getService();
    }

}
