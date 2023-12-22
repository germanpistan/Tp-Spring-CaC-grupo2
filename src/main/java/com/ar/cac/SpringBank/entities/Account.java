package com.ar.cac.SpringBank.entities;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import com.ar.cac.SpringBank.records.account.UpdateAccountRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type")
    @Enumerated(EnumType.ORDINAL)
    private AccountType type = AccountType.CAJA_AHORRO_PESOS;

    @Column(unique = true, precision = 22)
    private String cbu;

    @Column(unique = true)
    private String alias;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL
    )
    private List<Transfer> transfers = new ArrayList<>();


    public Account(User user, AccountType type, String cbu, String alias) {

        this.user = user;
        if (type != null) this.type = type;
        this.cbu = cbu;
        this.alias = alias;
    }

    public void update(UpdateAccountRecord record) {

        if (record.alias() != null) this.alias = record.alias();
    }

    public void updateAmount(BigDecimal amount) {

        this.amount = this.amount.add(amount);
    }
}
