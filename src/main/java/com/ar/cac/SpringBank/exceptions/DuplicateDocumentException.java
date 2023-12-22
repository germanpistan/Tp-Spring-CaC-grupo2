package com.ar.cac.SpringBank.exceptions;

public class DuplicateDocumentException extends Exception {

    public DuplicateDocumentException() {
        super("El documento ya se encuentra registrado.");
    }
}
