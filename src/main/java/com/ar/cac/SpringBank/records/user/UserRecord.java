package com.ar.cac.SpringBank.records.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRecord(
        @NotEmpty(
                message = "El id no puede ser nulo o estar vacio."
        )
        Long id,

        @NotEmpty(
                message = "El apellido no puede ser nulo o estar vacio."
        )
        @Min(
                value = 3, message = "El apellido no puede tener menos de 3 caracteres."
        )
        String lastName,

        @NotEmpty(
                message = "El nombre no puede ser nulo o estar vacio."
        )
        @Min(
                value = 3, message = "El nombre no puede tener menos de 3 caracteres."
        )
        String firstName,

        @NotEmpty(
                message = "El email no puede ser nulo o estar vacio."
        )
        @Email(
                message = "Formato de email incompatible."
        )
        String email,

        @NotEmpty(
                message = "El password no puede ser nulo o estar vacio."
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe tener una longitud minima de 8 caracteres y debe contener " +
                        "al menos: una letra minuscula, una  mayuscula, un numero y un caracter especial (caracteres aceptados: @$!%*?&)."
        )
        String password,

        @NotEmpty(
                message = "El documento no puede ser nulo o estar vacio."
        )
        @Pattern(
                regexp = "^\\d{8,10}$"
        )
        String document,

        @NotEmpty(
                message = "La fecha de cumpleaños no puede ser nula o estar vacia."
        )
        @JsonFormat(
                pattern = "yyyy-MM-dd"
        )
        LocalDate birthDate) {
}
