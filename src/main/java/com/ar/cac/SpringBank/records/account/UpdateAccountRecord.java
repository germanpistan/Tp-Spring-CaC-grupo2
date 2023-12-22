package com.ar.cac.SpringBank.records.account;

import jakarta.validation.constraints.NotEmpty;

public record UpdateAccountRecord(

        @NotEmpty(
                message = "El alias no puede ser nulo o estar vacio."
        )
        String alias
) {
}
