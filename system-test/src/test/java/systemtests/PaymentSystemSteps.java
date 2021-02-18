package systemtests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import clients.AccountServiceClient;
import clients.PaymentServiceClient;
import clients.TokenServiceClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Account;
import models.Payment;
import models.Token;

public class PaymentSystemSteps {

	private TokenServiceClient tokenServiceClient;
	private AccountServiceClient accountServiceClient;
	private PaymentServiceClient paymentServiceClient;

	List<Token> tokens;
	Payment paymentResult;
	Account merchantAccount;
	Account customerAccount;

	public PaymentSystemSteps() {
		this.tokenServiceClient = new TokenServiceClient();
		this.accountServiceClient = new AccountServiceClient();
		this.paymentServiceClient = new PaymentServiceClient();

	}

	@Given("a customer {string} {string} is registered in the system")
	public void aCustomerIsRegisteredInTheSystem(String firstName, String lastName) {
		this.customerAccount = this.accountServiceClient.createAccount(firstName, lastName, "customer");
	}

	@And("a merchant {string} {string} is registered in the system")
	public void aMerchantIsRegisteredInTheSystem(String firstName, String lastName) {
		this.merchantAccount = this.accountServiceClient.createAccount(firstName, lastName, "merchant");
	}

	@And("a valid token")
	public void aValidToken() {
		this.tokens = this.tokenServiceClient.createTokens(1);
	}

	@When("a payment of {double} usd is initiated with description of {string}")
	public void aPaymentIsInitiated(Double amount, String description) {
		this.paymentResult = this.paymentServiceClient.createPayment(this.customerAccount.getId(),
				this.merchantAccount.getId(), this.tokens.get(0).getTokenString(), description, amount);
	}

	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {
		assertNotNull(this.paymentResult);
	}

}
