package servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import payments.rest.models.ErrorModel;

public class PaymentServiceSteps {

    String token, customerId, merchantId, description;
    double amount;
    ErrorModel errorModel;

    private TestQueueClient testQueueClient;
    private PaymentServiceClient paymentServiceClient;

    public PaymentServiceSteps() {
        this.testQueueClient = new TestQueueClient();
        this.paymentServiceClient = new PaymentServiceClient();
    }

    @Given("a token {string}")
    public void aToken(String token) {
        this.token = token;
    }

    @And("a merchant {string}")
    public void aMerchant(String merchantId) {
        this.merchantId = merchantId;
    }

    @And("a customer {string}")
    public void aCustomer(String customerId) {
        this.customerId = customerId;
    }

    @And("a description of {string}")
    public void aDescriptionOf(String description) {
        this.description = description;
    }

    @And("an amount of {double} euro")
    public void anAmountOf(Double amount) {
        this.amount = amount;
    }

    @When("a payment is initiated")
    public void thePaymentIsInitiated() {
        this.errorModel = this.paymentServiceClient.createPayment(token, customerId, merchantId, description, amount);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertNotNull(this.errorModel);
    }

    @And("the response error message should say {string}")
    public void theErrorMessageIs(String message) {
        assertEquals(message, this.errorModel.getMessage());
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertNull(this.errorModel);
    }

}
