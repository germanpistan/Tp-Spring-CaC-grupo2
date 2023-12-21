package com.ar.cac.SpringBank.records.account;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import jakarta.validation.constraints.NotNull;

public record NewAccountRecord(
        @NotNull(
                message = "El id del usuario no puede ser nulo."
        )
        Long userId,
        AccountType type
) {
}
