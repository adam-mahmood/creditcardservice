package com.adammahmood.creditcardservice.validators;

import com.adammahmood.creditcardservice.model.CreditCard;
import org.springframework.stereotype.Service;

/**
 * A Generic Credit Card Validator
 */
@Service
public interface CreditCardValidator extends Validator<CreditCard> {
}
