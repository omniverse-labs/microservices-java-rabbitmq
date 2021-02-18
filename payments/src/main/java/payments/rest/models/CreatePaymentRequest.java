package payments.rest.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.ValidationException;

public class CreatePaymentRequest {
    private String token;
    private Double amount;
    private String merchantId;
    private String customerId;
    private String description;

    public CreatePaymentRequest() {
        super();
    }

    @JsonbCreator
    public CreatePaymentRequest(@JsonbProperty("amount") Double amount, @JsonbProperty("token") String token,
            @JsonbProperty("merchantId") String merchantId, @JsonbProperty("customerId") String customerId,
            @JsonbProperty("description") String description) {

        this.token = token;
        this.amount = amount;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.description = description;
    }

    public void validate() throws ValidationException {
        if (this.token == null || this.token.trim().isEmpty()) {
            throw new ValidationException("token is required");
        }

        if (this.merchantId == null || this.merchantId.trim().isEmpty()) {
            throw new ValidationException("merchant id is required");
        }

        if (this.customerId == null || this.customerId.trim().isEmpty()) {
            throw new ValidationException("customer id is required");
        }

        if (this.amount == null) {
            throw new ValidationException("amount is required");
        }
    }

    public String getToken() {
        return this.token;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getAmount() {
        return this.amount;
    }
}