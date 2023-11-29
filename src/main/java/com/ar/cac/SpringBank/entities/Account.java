package com.ar.cac.SpringBank.entities;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "cuentas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    private User owner;

    @OneToMany
    private List <Transfer> transfer;
}
