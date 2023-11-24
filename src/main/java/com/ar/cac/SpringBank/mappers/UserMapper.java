package com.ar.cac.SpringBank.mappers;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.UserDto;
import lombok.experimental.UtilityClass;



@UtilityClass
public class UserMapper {
    public static User dtoToUser (UserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setDocument(dto.getDocument());
        user.setPassword(dto.getPassword());
        user.setAddress(dto.getAddress());
        user.setBirthDate(dto.getBirthDate());
        return user;
    }

    public static UserDto userToDto (User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setDocument(user.getDocument());
        dto.setPassword(user.getPassword());
        dto.setAddress(user.getAddress());
        dto.setBirthDate(user.getBirthDate());
        return dto;

        // TODO: Esa es otra forma de hacerlo, tienes el constructor con todos los parametros.
        /* return new UserDto(user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.getDocument(),
                user.getBirthDate(),
                user.getAddress());
         */
    }
}
