package com.ar.cac.SpringBank.records.account;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record NewAccountRecord(
        @NotNull(
                message = "El id del usuario no puede ser nulo."
        )
        Long userId,

        @Enumerated(EnumType.ORDINAL)
        AccountType type
) {
}
