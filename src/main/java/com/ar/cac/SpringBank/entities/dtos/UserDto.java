package com.ar.cac.SpringBank.entities.dtos;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String document;
    private LocalDate birthDate;
    private String address;
    private boolean isEnabled = true;
}