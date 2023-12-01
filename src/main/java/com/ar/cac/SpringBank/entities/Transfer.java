package com.ar.cac.SpringBank.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;
    private Long origin;
    private Long target;
    private LocalDate date;
    private BigDecimal amount;


    //falta refactor aca en la relacion entre el creador de las cuentas y las transferencias.
    @ManyToOne
    private Account creator;

}
