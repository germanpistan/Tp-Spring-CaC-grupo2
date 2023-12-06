package com.ar.cac.SpringBank.Exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Usuario no encontrado.");
    }
}