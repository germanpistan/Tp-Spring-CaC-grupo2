package com.ar.cac.SpringBank.Exceptions;

public class DuplicateCbuException extends Exception {

    public DuplicateCbuException() {
        super("El cbu ya se encuentra registado");
    }
}
