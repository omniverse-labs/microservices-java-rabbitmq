Feature: Payment Microservices Solution

    Scenario: A payment is succesful in DTUPay
        Given a customer "Margarete" "White" is registered in the system
        And a merchant "Sarah" "Kelley" is registered in the system
        And a valid token
        When a payment of 100 usd is initiated with description of "system test payment 1"
        Then the payment is successful
