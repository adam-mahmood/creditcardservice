package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.CreditCard;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
/**
 * This validator performs basic validation on the credit card number such as null check, digit type check and length check. Credit card
 * lengths vary between 12 and 19 digits
 * This sort of validation should be done on the client side
 */
public class BasicCreditCardValidator implements CreditCardValidator {

    /**
     * This regex checks the following logic:
     * - Visa : 13 or 16 digits, starting with 4.
     * - MasterCard : 16 digits, starting with 51 through 55.
     * - Discover : 16 digits, starting with 6011 or 65.
     * - American Express : 15 digits, starting with 34 or 37.
     * - Diners Club : 14 digits, starting with 300 through 305, 36, or 38.
     * - JCB : 15 digits, starting with 2131 or 1800, or 16 digits starting with 35.
     */
    public final String CREDIT_CARD_NUMBER_REGEX = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
            "(?<mastercard>5[1-5][0-9]{14})|" +
            "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
            "(?<amex>3[47][0-9]{13})|" +
            "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
            "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

    @Override
    public boolean validate(CreditCard card){
        log.info("Performing Basic Validation on Cedit Card {}", card);
         @NotNull  String creditCardNumber = card.getCreditCardNumber();

        if(StringUtils.isBlank(creditCardNumber)){
            String errorMsg = "Credit Card Number cannot be blank";
            log.error(errorMsg);
            throw new InvalidCreditCardException(errorMsg);
        }

        //Strip all hyphens
        creditCardNumber = creditCardNumber.replaceAll("-", "");

        Pattern pattern = Pattern.compile(CREDIT_CARD_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(creditCardNumber);

        if (!matcher.matches()){
            String errorMsg = String.format("Credit Card Number %s is invalid as it contain a non-digit, invalid structure or has an invalid length", creditCardNumber);
            log.error(errorMsg);
            throw new InvalidCreditCardException(errorMsg);
        }

        return true;
    }
}
