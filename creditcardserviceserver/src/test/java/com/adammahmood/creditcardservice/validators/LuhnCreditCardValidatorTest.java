package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LuhnCreditCardValidatorTest {

    private LuhnCreditCardValidator validator;

    @BeforeEach
    void init() {
        validator = new  LuhnCreditCardValidator();
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

    @ParameterizedTest(name = "creditCardNumber:{0} = isValid:{1}")
    @CsvSource({
            "49927398717,false",
            "1234567812345678,false",
            "79927398710,false",
            "79927398711,false",
            "79927398712,false",

    })
    void validateInvalidNumbers(String creditCardNumber,boolean isValid) {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber(creditCardNumber);
        assertThrows(InvalidCreditCardException.class,() -> {
            validator.validate(card);
        });
    }

}