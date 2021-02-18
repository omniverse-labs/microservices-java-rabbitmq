Feature: Payment

    Scenario: Invalid Token
        Given a token "some-token"
        And a merchant "test_valid_merchant_id"
        And a customer "test_valid_customer_id"
        And a description of "test_description"
        And an amount of 10 euro
        When a payment is initiated
        Then the payment is not successful
        And the response error message should say "token is invalid"

    Scenario: Invalid Customer
        Given a token "test_valid_token"
        And a merchant "test_valid_merchant_id"
        And a customer "some-unknown-customer-id"
        And a description of "test_description"
        And an amount of 10 euro
        When a payment is initiated
        Then the payment is not successful
        And the response error message should say "customer id is invalid"

    Scenario: Successful Payment
        Given a token "test_valid_token"
        And a merchant "test_valid_merchant_id"
        And a customer "test_valid_customer_id"
        And a description of "test_description"
        And an amount of 10 euro
        When a payment is initiated
        Then the payment is successful