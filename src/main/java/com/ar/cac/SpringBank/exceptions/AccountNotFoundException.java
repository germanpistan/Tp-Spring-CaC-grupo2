package com.ar.cac.SpringBank.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException() {
        super("La cuenta no existe.");
    }
}
