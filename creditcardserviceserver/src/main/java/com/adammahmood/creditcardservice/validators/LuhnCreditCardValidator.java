package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.CreditCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
/**
 * This validator applies the luhn 10 algorithm to validate a credit card number
 */
public class LuhnCreditCardValidator implements CreditCardValidator {

    @Override
    public boolean validate(CreditCard card){
        @NotNull String creditCardNumber = card.getCreditCardNumber();
        log.info("Performing Luhn 10 algorithm on {}",creditCardNumber);
        int nDigits = creditCardNumber.length();

        int sum = 0;
        boolean isEverySecondDigit = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = creditCardNumber.charAt(i) - '0';

            //double every second digit
            if (isEverySecondDigit){
                d = d * 2;
            }

            // Now take the sum of all the digits.
            // We add two digits to handle
            // cases that make two digits
            // after doubling
            sum += d / 10;
            sum += d % 10;

            isEverySecondDigit = !isEverySecondDigit;
        }
        boolean isvalid = sum % 10 == 0;
        if (!isvalid){
            String errorMsg = String.format("Credit Card Number %s Fails against the Luhn 10 algorithm", card.getCreditCardNumber());
            log.error(errorMsg);
            throw new InvalidCreditCardException(errorMsg);
        }
        return isvalid;
    }
}
