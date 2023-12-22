package com.ar.cac.SpringBank.exceptions;

public class TransferNotFoundException extends Exception {
    public TransferNotFoundException() {
        super("La transferencia no fue encontrada");
    }
}
