package com.ar.cac.SpringBank.entities.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Long origin;
    private Long target;
    private LocalDate date;
    private BigDecimal amount;
}
