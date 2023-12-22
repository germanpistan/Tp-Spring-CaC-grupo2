package com.ar.cac.SpringBank.records.account;

import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.enums.AccountType;
import com.ar.cac.SpringBank.records.transfer.TransferRecord;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record AccountRecord(
        Long id,
        @Enumerated(EnumType.STRING)
        AccountType type,
        String cbu,
        String alias,
        BigDecimal amount,
        List<TransferRecord> transfers
) {

    public AccountRecord(Account account) {

        this(
                account.getId(),
                account.getType(),
                account.getCbu(),
                account.getAlias(),
                account.getAmount(),
                new ArrayList<>(
                        account.getTransfers()
                                .stream()
                                .map(TransferRecord::new)
                                .toList()
                )
        );
    }
}
