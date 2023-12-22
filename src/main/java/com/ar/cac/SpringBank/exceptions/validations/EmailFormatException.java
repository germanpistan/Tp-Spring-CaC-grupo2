package com.ar.cac.SpringBank.exceptions.validations;

public class EmailFormatException extends Exception {

    public EmailFormatException() {
        super("Formato de email incorrecto.");
    }
}
