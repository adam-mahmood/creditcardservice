package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.CreditCard;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
/**
 * This validator performs basic validation on the credit card number such as null check and length check. Credit card
 * lengths vary between 12 and 19 digits
 * This sort of validation should be done on the client side
 */
public class BasicCreditCardValidator implements CreditCardValidator {

    @Override
    public boolean validate(CreditCard card){
        log.info("Performing Basic Validation on Cedit Card {}", card);
        String creditCardNumber = card.getCreditCardNumber();

        if(StringUtils.isBlank(creditCardNumber)){
            String errorMsg = "Credit Card Number cannot be blank";
            log.error(errorMsg);
            throw new InvalidCreditCardException(errorMsg);
        }


        int nDigits = creditCardNumber.length();

        // This length limits were acquired from wikipedia
        if (nDigits < 11 || nDigits > 19){
            String errorMsg = String.format("Credit Card Number length %s is not the required length, which is between 12 and 19 digits", nDigits);
            log.error(errorMsg);
            throw new InvalidCreditCardException(errorMsg);
        }
        return true;
    }
}
