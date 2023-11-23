package com.ar.cac.SpringBank.entities;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Los nombres de las tablas no se escriben en camel case, sino con guiones bajos para separar las palabras.
    @Column(name = "accountType", nullable = false)
    private AccountType type;

    // TODO: El cbu y el alias deben ser unicos. También agregale la anotación en todos los atributos.
    private String cbu;

    private String alias;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;


    //private User owner;

}
