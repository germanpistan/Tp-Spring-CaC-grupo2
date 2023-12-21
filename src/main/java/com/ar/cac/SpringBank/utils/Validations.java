package com.ar.cac.SpringBank.utils;

import com.ar.cac.SpringBank.Exceptions.validations.EmailFormatException;

public class Validations {

    public static void emailValidator(String s) throws EmailFormatException {

        boolean result = s.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!result) throw new EmailFormatException();
    }
}
