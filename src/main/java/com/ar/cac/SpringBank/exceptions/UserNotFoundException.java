package com.ar.cac.SpringBank.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Usuario no encontrado.");
    }
}