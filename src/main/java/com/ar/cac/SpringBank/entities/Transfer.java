package com.ar.cac.SpringBank.entities;

import com.ar.cac.SpringBank.records.transfer.TransferRecord;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transfers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "source_account_id")
    private Long sourceAccountId;
    @Column(name = "target_account_id")
    private Long targetAccountId;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;
    private BigDecimal amount = BigDecimal.ZERO;
    @ManyToOne
    private Account creator;

    public Transfer(TransferRecord record) {

        this.id = record.id();
        this.sourceAccountId = record.sourceAccount();
        this.targetAccountId = record.targetAccount();
        this.date = record.date();
        this.amount = record.amount();
    }
}
