package com.ar.cac.SpringBank.exceptions;

public class InsufficientFoundsException extends Exception {
    public InsufficientFoundsException(String message) {
        super(message);
    }

    public InsufficientFoundsException() {
        super("Fondos insuficientes.");
    }
}
