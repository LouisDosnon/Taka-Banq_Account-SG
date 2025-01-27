package org.kata.exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount cannot be negative.");
    }
}
