package com.ar.cac.SpringBank.Exceptions;

public class DuplicateDocumentException extends Exception {

    public DuplicateDocumentException() {
        super("El documento ya se encuentra registrado.");
    }
}
