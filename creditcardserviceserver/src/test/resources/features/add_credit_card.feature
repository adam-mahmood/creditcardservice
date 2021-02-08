@integrationTests
Feature: a credit card can be added

  Scenario Outline: client makes call a POST call to /<branchId>/<customerId>/<endpoint>
    Given a customer from branch with id of <branchID> and guid of <guid>
    When the client calls the <endpoint>  endpoint to add a credit card
    And with request:
      | name | creditCardNumber | creditLimit | cvd | expiryMonth | expiryYear | cardNetwork |
      | JOHN DIGGLES | <creditCardNumber> | 2500 | 123 | 08 | 22 | VISA |
    Then the client receives status code of <response>
    And the client receives message <message>
    Examples:
      | branchID | guid | endpoint | creditCardNumber | response | message |
      | 1 | "d290f1ee-6c54-4b01-90e6-d701748f0851" | "creditcards" | 79927398713 | 201 | "created" |
      | 1 | "d290f1ee-6c54-4b01-90e6-d701748f0851" | "creditcards" | 79927398710 | 400 | "Credit Card Number 79927398710 Fails against the Luhn 10 algorithm" |
