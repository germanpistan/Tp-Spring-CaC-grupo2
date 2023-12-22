package com.ar.cac.SpringBank.exceptions;

public class DuplicateCbuException extends Exception {

    public DuplicateCbuException() {
        super("El cbu ya se encuentra registado");
    }
}
