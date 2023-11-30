package com.ar.cac.SpringBank.Exceptions;

public class InsufficientFoundsException extends RuntimeException{
    public InsufficientFoundsException(String message) {
        super(message);
    }
}
