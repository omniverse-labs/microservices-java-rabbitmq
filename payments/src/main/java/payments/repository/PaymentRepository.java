package payments.repository;

import payments.businesslogic.models.Payment;

public interface PaymentRepository {
    void savePayment(Payment payment);

    Payment getPayment(String id);
}
