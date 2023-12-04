package com.ar.cac.SpringBank.Exceptions;

public class DuplicateAliasException extends Exception{

    private static String message= "El cbu ya se encuentra registado";

    public DuplicateAliasException(){
        super(message);
    }
}
