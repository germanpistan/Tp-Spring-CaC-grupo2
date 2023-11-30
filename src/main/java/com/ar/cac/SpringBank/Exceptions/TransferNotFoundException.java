package com.ar.cac.SpringBank.Exceptions;

public class TransferNotFoundException extends RuntimeException{
    public TransferNotFoundException() {
        super("La transferencia no fue encontrada");
    }
}
