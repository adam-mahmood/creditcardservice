package com.adammahmood.creditcardservice.controllers;

import com.adammahmood.creditcardservice.exceptions.InvalidCreditCardException;
import com.adammahmood.creditcardservice.model.ErrorDetials;
import com.adammahmood.creditcardservice.model.JSONAPIErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = {InvalidCreditCardException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetials handleBadRequest(final  Exception e){

        return getErrorDetails(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetials handleInternalServerError(final  Exception e){

        return getErrorDetails(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    private ErrorDetials getErrorDetails(final HttpStatus status, final String details){
        ErrorDetials errorDetials = new ErrorDetials();
        JSONAPIErrorDetails jsonapiErrorDetails = new JSONAPIErrorDetails()
        .code(status.value())
        .details(details)
        .message(details);
        errorDetials.addErrorsItem(jsonapiErrorDetails);
        return errorDetials;
    }
}
