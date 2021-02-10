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

    @ParameterizedTest(name = "creditCardNumber:{0}")
    @CsvSource({
            "123456789012345567888",
            "123456789012345567888",
            "5555555",
            "398282246310005",
            "3530"
    })
    void invalidCreditCardNumberLengthOf20Test(String creditCardNumber) {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber("123456789012345567888");
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

    /**
     * see https://www.paypalobjects.com/en_GB/vhelp/paypalmanager_help/credit_card_numbers.htm for test numbers
     * @param creditCardNumber
     * @param isValid
     */
    @ParameterizedTest(name = "creditCardNumber:{0} = isValid:{1}")
    @CsvSource({
            "4242424242424242,true",
            "4000056655665556,true",
            "5555555555554444,true",
            "378282246310005,true",
            "3530111333300000, true"
    })
    void validateValidNumbers(String creditCardNumber,boolean isValid) {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber(creditCardNumber);
        assertEquals(isValid,validator.validate(card));
    }
}