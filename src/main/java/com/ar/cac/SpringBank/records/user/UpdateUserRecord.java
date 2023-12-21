package com.ar.cac.SpringBank.records.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateUserRecord(

        @Min(
                value = 3, message = "El apellido no puede tener menos de 3 caracteres."
        )
        String lastName,

        @Min(
                value = 3, message = "El nombre no puede tener menos de 3 caracteres."
        )
        String firstName,
        @Email
        String email,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe tener una longitud minima de 8 caracteres y debe contener " +
                        "al menos: una letra minuscula, una  mayúscula, un numero y un caracter especial (caracteres aceptados: @$!%*?&)."
        )
        String password,

        @Pattern(
                regexp = "^\\d{8,10}$"
        )
        String document,

        @NotEmpty(
                message = "La direccion no puede ser nula o estar vacia."
        )
        String address,

        @JsonFormat(
                pattern = "yyyy-MM-dd"
        )
        LocalDate birthDate) {
}
