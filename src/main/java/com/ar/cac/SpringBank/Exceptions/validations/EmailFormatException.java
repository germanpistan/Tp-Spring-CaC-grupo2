package com.ar.cac.SpringBank.Exceptions.validations;

public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super("Formato de email incorrecto.");
    }
}
