package com.ar.cac.SpringBank.Exceptions;

public class DuplicateDocumentException extends Exception {

    public DuplicateDocumentException() {
        super("Este documento ya se encuentra registrado.");
    }
}
