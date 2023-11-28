package com.ar.cac.SpringBank.entities;

import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;

public class Transfer {
    private Long id;
    private Long origin;
    //falta target
    private Date date;
    private BigDecimal amount;





    //falta refactor aca en la relacion entre el creador de las cuentas y las transferencias.
    @ManyToOne
    private Account creator;

}
