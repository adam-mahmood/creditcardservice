package com.adammahmood.creditcardservice.validators;

/**
 * A Generic Validator
 * @param <T>
 */
public interface Validator<T> {
    boolean validate(T t);
}
