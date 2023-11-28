package com.ar.cac.SpringBank.entities;

import jakarta.persistence.ManyToOne;

public class Transfer {
    private Long id;
    private Long origin;





    //falta refactor aca en la relacion entre el creador de las cuentas y las transferencias.
    @ManyToOne
    private Account creator;

}
