package com.ar.cac.SpringBank.entities.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Long sourceAccountId;
    private Long targetAccountId;
    //private LocalDateTime date; Ya no lo requiere, se genera automáticamente
    private BigDecimal amount;
}
