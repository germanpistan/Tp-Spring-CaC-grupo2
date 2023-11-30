package com.ar.cac.SpringBank.Exceptions;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String message) {
        super(message);
    }
}