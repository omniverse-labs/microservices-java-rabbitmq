package payments.businesslogic.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private String id;
    private String token;
    private Double amount;
    private String merchantId;
    private String customerId;
    private String description;
    private LocalDateTime time;

    public Payment(String token, String merchantId, String customerId, String description, Double amount) {
        this.id = UUID.randomUUID().toString();
        this.token = token;
        this.amount = amount;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
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

    public LocalDateTime getTime() {
        return this.time;
    }

}
