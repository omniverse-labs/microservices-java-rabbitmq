package payments.repository;

import java.util.ArrayList;
import java.util.List;

import payments.businesslogic.models.Payment;

public class InMemoryPaymentRepository implements PaymentRepository {

    private static List<Payment> payments;

    public InMemoryPaymentRepository() {
        payments = new ArrayList<Payment>();
    }

    @Override
    public void savePayment(Payment payment) {
        payments.add(payment);
    }

    @Override
    public Payment getPayment(String id) {
        return payments.stream().filter(payment -> payment.getId().equals(id)).findFirst().orElse(null);
    }

}
