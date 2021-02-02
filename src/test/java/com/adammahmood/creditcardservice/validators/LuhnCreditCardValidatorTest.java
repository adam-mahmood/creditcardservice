package com.adammahmood.creditcardservice.validators;

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
                "49927398717,false",
                "1234567812345678,false",
                "1234567812345670,true",
    })
    void validate(String creditCardNumber,boolean isValid) {
        CreditCard card = new CreditCard();
        card.setCreditCardNumber(creditCardNumber);
        validator.validate(card);
    }


    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, sum={2}")
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5"
    })
    void sum(int a, int b, int sum) {
        assertEquals(sum, a + b);
    }




}