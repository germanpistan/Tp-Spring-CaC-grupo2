package com.ar.cac.SpringBank.entities.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Long origin;
    private Long target;
    private Date date;
    private BigDecimal amount;
}
