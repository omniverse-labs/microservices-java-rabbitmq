package unittests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import payments.businesslogic.exceptions.InvalidAccount;
import payments.businesslogic.exceptions.InvalidToken;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.interfaces.PaymentService;
import payments.businesslogic.interfaces.QueueService;
import payments.businesslogic.models.AccountInfo;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.services.SimplePaymentService;
import payments.repository.PaymentRepository;

public class PaymentServiceSteps {

    String token;
    Double amount;
    String customerId;
    String merchantId;
    String description;
    Payment payment;

    QueueService queueService;
    PaymentService paymentService;
    PaymentRepository paymentRepository;

    private void resetLocalVars() {
        this.amount = 100.0;
        this.token = "token";
        this.customerId = "customerId";
        this.merchantId = "merchantId";
        this.description = "description";

        queueService = mock(QueueService.class);
        paymentRepository = mock(PaymentRepository.class);

        paymentService = new SimplePaymentService(paymentRepository, queueService);
    }

    @Before
    public void initialization() {
        resetLocalVars();
    }

    @Given("An invalid token {string}")
    public void anInvalidToken(String token) throws QueueException {
        this.token = token;
        when(queueService.validateToken(token)).thenReturn(new TokenInfo(token, false));
    }

    @When("Creating a payment")
    public void creatingPaymentWithToken() {
        this.payment = new Payment(token, merchantId, customerId, description, amount);
    }

    @Then("An exception of type InvalidToken is thrown")
    public void anExceptionOfTypeInvalidTokenIsThrown() {
        assertThrows(InvalidToken.class, () -> this.paymentService.createPayment(payment));
    }

    @Given("A valid token {string}")
    public void aValidToken(String token) throws QueueException {
        this.token = token;
        when(queueService.validateToken(token)).thenReturn(new TokenInfo(token, true));
    }

    @And("A valid merchant id {string}")
    public void aValidMerchantId(String merchantId) throws QueueException {
        this.merchantId = merchantId;
        when(queueService.validateAccount(merchantId)).thenReturn(new AccountInfo(merchantId, true));
    }

    @And("An invalid customer id {string}")
    public void anInvalidCustomerId(String customerId) throws QueueException {
        this.customerId = customerId;
        when(queueService.validateAccount(customerId)).thenReturn(new AccountInfo(customerId, false));
    }

    @Then("An exception of type InvalidAccount is thrown")
    public void anExceptionOfTypeInvalidAccountIsThrown() {
        assertThrows(InvalidAccount.class, () -> this.paymentService.createPayment(payment));
    }

    @And("A valid customer id {string}")
    public void aValidCustomerId(String customerId) throws QueueException {
        this.customerId = customerId;
        when(queueService.validateAccount(customerId)).thenReturn(new AccountInfo(customerId, true));
    }

    @And("An invalid merchant id {string}")
    public void anInvalidMerchantId(String merchantId) throws QueueException {
        this.merchantId = merchantId;
        when(queueService.validateAccount(merchantId)).thenReturn(new AccountInfo(merchantId, false));
    }

    @Then("The payment is saved and published")
    public void aPaymentIsSavedandPublished() throws QueueException, InvalidToken, InvalidAccount {

        this.paymentService.createPayment(payment);

        verify(paymentRepository).savePayment(any());
        verify(queueService).publishPaymentCreatedEvent(any());
    }

}
