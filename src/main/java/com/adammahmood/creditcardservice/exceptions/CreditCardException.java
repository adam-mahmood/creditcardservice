package com.adammahmood.creditcardservice.exceptions;

public class CreditCardException extends RuntimeException {
    public CreditCardException(String message) {
        super(message);
    }

    public CreditCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
