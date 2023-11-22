package com.ar.cac.SpringBank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountType", nullable = false)
    private AccessType type;

    private String cbu;

    private String alias;

    @Column(name = "amount", nullable = false)
    private Double amount;

    //TODO
    private String owner;

}
