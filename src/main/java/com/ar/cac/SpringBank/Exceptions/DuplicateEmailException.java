package com.ar.cac.SpringBank.Exceptions;

public class DuplicateEmailException extends Exception {

    public DuplicateEmailException() {
        super("La dirección de correo ya se encuentra registrada.");
    }
}
