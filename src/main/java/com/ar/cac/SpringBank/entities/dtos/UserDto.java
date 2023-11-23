package com.ar.cac.SpringBank.entities.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String document;
    private LocalDate birthDate;
    private String address;
}