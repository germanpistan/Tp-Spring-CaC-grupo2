package com.ar.cac.SpringBank.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TransferDto {
    private Long id;
    private Long origin;
    private Long target;
    private LocalDate date;
    private BigDecimal amount;

}
