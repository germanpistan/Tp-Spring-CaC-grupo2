package com.ar.cac.SpringBank.entities.dtos;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private AccountType type;

    private String cbu;

    private String alias;

    private BigDecimal amount;

}
