package com.ar.cac.SpringBank.Exceptions;

public class DuplicateCbuException extends Exception{
     private static String message= "El cbu ya se encuentra registado";
    public DuplicateCbuException(){
        super(message);
    }
}
