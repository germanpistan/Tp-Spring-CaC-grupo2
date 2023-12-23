package com.ar.cac.SpringBank.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException() {
        super("Fondos insuficientes.");
    }
}
