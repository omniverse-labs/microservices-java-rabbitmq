package payments.businesslogic.services;

import payments.businesslogic.exceptions.InvalidAccount;
import payments.businesslogic.exceptions.InvalidToken;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.interfaces.PaymentService;
import payments.businesslogic.interfaces.QueueService;
import payments.businesslogic.models.Payment;
import payments.repository.PaymentRepository;

public class SimplePaymentService implements PaymentService {

    private QueueService queueService;
    private PaymentRepository paymentRepository;

    public SimplePaymentService(PaymentRepository paymentRepository, QueueService queueService) {
        this.queueService = queueService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment payment) throws InvalidToken, InvalidAccount, QueueException {

        // 1. check token with token service
        var tokenInfo = this.queueService.validateToken(payment.getToken());
        if (!tokenInfo.getIsValid()) {
            throw new InvalidToken("token is invalid");
        }

        // 2. check customer id with accounts service
        var customerInfo = this.queueService.validateAccount(payment.getCustomerId());
        if (!customerInfo.getIsValid()) {
            throw new InvalidAccount("customer id is invalid");
        }

        // 3. check merchant id with accounts service
        var merchantInfo = this.queueService.validateAccount(payment.getMerchantId());
        if (!merchantInfo.getIsValid()) {
            throw new InvalidAccount("merchant id is invalid");
        }

        // 4. save payment
        this.paymentRepository.savePayment(payment);

        // 5. publish payment created event
        this.queueService.publishPaymentCreatedEvent(payment);

        // 6. return payment
        return payment;
    }

    @Override
    public Payment getPayment(String id) {
        return this.paymentRepository.getPayment(id);
    }

}
