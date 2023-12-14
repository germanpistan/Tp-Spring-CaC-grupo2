package com.ar.cac.SpringBank.entities.dtos;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    private AccountType type;

    private String cbu;

    private String alias;

    private BigDecimal amount = BigDecimal.ZERO;

    private Long userId;

}
