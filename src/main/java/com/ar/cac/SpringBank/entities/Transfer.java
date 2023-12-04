package com.ar.cac.SpringBank.entities;

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
    //@Column(name = "transfer_id") Lo quito para seguir un patron
    private Long id;
    @Column(name = "source_account_id", nullable = false)
    private Long sourceAccountId;
    @Column(name = "target_account_id", nullable = false)
    private Long targetAccountId;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;
    private BigDecimal amount = BigDecimal.ZERO;


    //falta refactor aca en la relacion entre el creador de las cuentas y las transferencias.
    @ManyToOne
    private Account creator;

}
