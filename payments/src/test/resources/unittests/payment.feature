Feature: Payment Service

    Scenario: : Token is invalid
        Given An invalid token "invalid_token"
        When Creating a payment
        Then An exception of type InvalidToken is thrown

    Scenario: : Customer Id is invalid
        Given A valid token "valid_token"
        And A valid merchant id "valid_merchant_id"
        And An invalid customer id "invalid_customer_id"
        When Creating a payment
        Then An exception of type InvalidAccount is thrown

    Scenario: : Merchant Id is invalid
        Given A valid token "valid_token"
        And A valid customer id "valid_customer_id"
        And An invalid merchant id "invalid_merchant_id"
        When Creating a payment
        Then An exception of type InvalidAccount is thrown

    Scenario: All payment data are valid
        Given A valid token "valid_token"
        And A valid customer id "valid_customer_id"
        And A valid merchant id "valid_merchant_id"
        When Creating a payment
        Then The payment is saved and published