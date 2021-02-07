package com.adammahmood.creditcardservice.exceptions;

public class InvalidCreditCardException extends CreditCardException {

    public InvalidCreditCardException(String errorMsg){
        super(errorMsg);
    }
    public InvalidCreditCardException(String errorMsg,Throwable err){
        super(errorMsg,err);
    }
}
