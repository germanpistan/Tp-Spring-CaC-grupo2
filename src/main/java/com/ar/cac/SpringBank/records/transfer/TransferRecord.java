package com.ar.cac.SpringBank.records.transfer;

import com.ar.cac.SpringBank.entities.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRecord(
        Long id,
        Long accountId,
        String targetCBU,
        BigDecimal amount,
        LocalDateTime date
) {

    public TransferRecord(Transfer transfer) {

        this(
                transfer.getId(),
                transfer.getAccount().getId(),
                transfer.getTargetAccount(),
                transfer.getAmount(),
                transfer.getDate()
        );
    }
}
