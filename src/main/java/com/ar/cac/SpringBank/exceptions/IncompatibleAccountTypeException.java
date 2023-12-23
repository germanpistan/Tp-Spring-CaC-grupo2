package com.ar.cac.SpringBank.exceptions;

public class IncompatibleAccountTypeException extends Exception {
    public IncompatibleAccountTypeException() {
        super("El tipo de cuenta no es compatible.");
    }
}
