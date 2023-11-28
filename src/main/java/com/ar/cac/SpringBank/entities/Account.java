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

    @Column(name = "account_type", nullable = false)
    private AccountType type;

    @Column(name = "cbu",nullable = false, unique = true)
    private String cbu;

    @Column(name = "alias",nullable = false, unique = true)
    private String alias;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;


    //private User owner;

}
