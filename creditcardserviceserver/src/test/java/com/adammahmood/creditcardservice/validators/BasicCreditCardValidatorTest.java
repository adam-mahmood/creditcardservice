package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BasicCreditCardValidatorTest {
    private CreditCardValidator validator;

    @BeforeEach
    void init() {
        validator = new  BasicCreditCardValidator();
    }

    @Test
    void emptyCreditCardNumberTest() {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber("");
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

    @Test
    void nullCreditCardNumberTest() {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber(null);
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

    @Test
    void invalidCreditCardNumberLengthOf5Test() {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber("12345");
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

    @Test
    void invalidCreditCardNumberLengthOf20Test() {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber("123456789012345567888");
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

    @ParameterizedTest(name = "creditCardNumber:{0} = isValid:{1}")
    @CsvSource({
            "49927398716,true",
            "1234567812345670,true",
            "79927398713,true"
    })
    void validateValidNumbers(String creditCardNumber,boolean isValid) {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber(creditCardNumber);
        assertEquals(isValid,validator.validate(card));
    }
}