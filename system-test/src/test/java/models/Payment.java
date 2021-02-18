package models;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Payment {
    private String id;
    private String token;
    private Double amount;
    private String merchantId;
    private String customerId;
    private String description;
    private LocalDateTime time;

    @JsonbCreator
    public Payment(@JsonbProperty("id") String id, @JsonbProperty("token") String token,
            @JsonbProperty("merchantId") String merchantId, @JsonbProperty("customerId") String customerId,
            @JsonbProperty("description") String description, @JsonbProperty("amount") Double amount,
            @JsonbProperty("time") LocalDateTime time) {
        this.id = id;
        this.token = token;
        this.amount = amount;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.description = description;
        this.time = time;
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
