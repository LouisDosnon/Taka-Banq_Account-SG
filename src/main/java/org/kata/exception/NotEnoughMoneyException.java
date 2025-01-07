package org.kata.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("You do not have the amount necessary for this operation.");
    }
}
