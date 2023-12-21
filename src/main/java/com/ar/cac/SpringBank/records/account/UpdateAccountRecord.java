package com.ar.cac.SpringBank.records.account;

import com.ar.cac.SpringBank.entities.enums.AccountType;

import java.math.BigDecimal;

public record UpdateAccountRecord(
        AccountType type,
        String cbu,
        String alias,
        BigDecimal amount
) {
}
