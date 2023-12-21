package com.ar.cac.SpringBank.Exceptions;

public class DuplicateAliasException extends Exception {

    public DuplicateAliasException() {
        super("El alias ya se encuentra registado");
    }
}
