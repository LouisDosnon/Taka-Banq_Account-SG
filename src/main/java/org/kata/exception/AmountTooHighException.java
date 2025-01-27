package org.kata.exception;

public class AmountTooHighException extends RuntimeException {
    public AmountTooHighException() {
        super("Amount is too high.");
    }
}
