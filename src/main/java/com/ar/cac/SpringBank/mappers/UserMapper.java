package com.ar.cac.SpringBank.mappers;

import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public User dtoToUser(UserDto dto) {
        return User.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .document(dto.getDocument())
                .email(dto.getEmail())
                .id(dto.getId())
                .password(dto.getPassword())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .build();
    }

    public static UserDto userToDto(User user) {
        return UserDto.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .document(user.getDocument())
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .build();
    }
}
