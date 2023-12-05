package com.ar.cac.SpringBank.Exceptions;

public class DuplicateAliasException extends Exception{

    public DuplicateAliasException(){
        super("El cbu ya se encuentra registado");
    }
}
