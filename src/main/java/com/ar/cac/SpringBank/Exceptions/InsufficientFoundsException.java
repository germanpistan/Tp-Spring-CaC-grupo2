package com.ar.cac.SpringBank.Exceptions;

public class InsufficientFoundsException extends Exception {
    public InsufficientFoundsException(String message) {
        super(message);
    }

    public InsufficientFoundsException() {
        super("Fondos insuficientes");
    }
}
