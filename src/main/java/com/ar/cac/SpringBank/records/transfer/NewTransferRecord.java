package com.ar.cac.SpringBank.records.transfer;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewTransferRecord(
        @NotNull(
                message = "El id de la cuenta de origen no puede ser nulo."
        )
        Long accountId,

        @NotNull(
                message = "El id de la cuenta de destino no puede ser nulo."
        )
        Long targetAccountId,

        @NotNull(
                message = "El monto no puede ser nulo."
        )
        BigDecimal amount
) {
}
