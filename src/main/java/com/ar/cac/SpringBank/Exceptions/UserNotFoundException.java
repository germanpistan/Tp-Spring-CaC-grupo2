package com.ar.cac.SpringBank.Exceptions;

public class UserNotFoundException extends Exception {
    /*public UserNotExistsException(UserFinal userDestination) {
        switch (userDestination) {
            case USUARIO_ORIGEN -> super("El usuario de origen no existe");

            case USUARIO_DESTINO -> {
                super("El usuario de destino no existe");
            }
        }
    }*/

    public UserNotFoundException() {
        super("Usuario no encontrado.");
    }
}