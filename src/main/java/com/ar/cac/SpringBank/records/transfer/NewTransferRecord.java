package com.ar.cac.SpringBank.records.transfer;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewTransferRecord(
        @NotNull(
                message = "El id del usuario de origen no puede ser nulo."
        )
        Long sourceAccountId,

        @NotNull(
                message = "El id del usuario de destino no puede ser nulo."
        )
        Long targetAccountId,

        @NotNull(
                message = "El monto no puede ser nulo."
        )
        BigDecimal amount
) {
}
