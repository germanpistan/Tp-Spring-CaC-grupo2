package com.ar.cac.SpringBank.Exceptions;

public class TransferNotFoundException extends RuntimeException{
    public TransferNotFoundException(String message) {
        super(message);
    }
}
