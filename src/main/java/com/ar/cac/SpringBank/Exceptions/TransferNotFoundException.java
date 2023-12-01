package com.ar.cac.SpringBank.Exceptions;

public class TransferNotFoundException extends Exception {
    public TransferNotFoundException() {
        super("La transferencia no fue encontrada");
    }
}
