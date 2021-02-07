package com.adammahmood.creditcardservice.exceptions;

public class RecordNotFoundException extends CreditCardException {
    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
