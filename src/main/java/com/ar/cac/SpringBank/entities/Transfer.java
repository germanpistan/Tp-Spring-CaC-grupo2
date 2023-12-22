package com.ar.cac.SpringBank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transfers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_cbu", nullable = false)
    private String targetAccount;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    public Transfer(Account account, Account targetAccount, BigDecimal amount) {

        this.account = account;
        this.targetAccount = targetAccount.getCbu();
        this.amount = amount;
    }
}
