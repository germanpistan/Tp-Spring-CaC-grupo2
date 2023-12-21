package com.ar.cac.SpringBank.records.transfer;

import com.ar.cac.SpringBank.entities.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRecord(
        Long id,
        Long sourceAccount,
        Long targetAccount,
        BigDecimal amount,
        LocalDateTime date
) {

    public TransferRecord(Transfer transfer) {

        this(
                transfer.getId(),
                transfer.getSourceAccountId(),
                transfer.getTargetAccountId(),
                transfer.getAmount(),
                transfer.getDate()
        );
    }
}
