package com.ar.cac.SpringBank.Exceptions;

import com.ar.cac.SpringBank.Exceptions.enums.UserFinal;

import static com.ar.cac.SpringBank.Exceptions.enums.UserFinal.*;


public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(UserFinal userDestination) {
        switch (userDestination){
            case USUARIO_ORIGEN -> {
                super("El usuario de origen no existe");
            }
            case  USUARIO_DESTINO -> {
                super("El usuario de destino no existe");
            }
        }
    }

    public UserNotExistsException(){
        super("El usuario no existe");
    }
}