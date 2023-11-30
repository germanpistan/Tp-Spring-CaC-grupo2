package com.ar.cac.SpringBank.services;
import com.ar.cac.SpringBank.Exceptions.UserNotExistsException;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.UserDto;
import com.ar.cac.SpringBank.mappers.UserMapper;
import com.ar.cac.SpringBank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        List<UserDto> usersDtos = users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
        return usersDtos;
    }

    public UserDto createUser(UserDto userDto) throws UserNotExistsException {
        User userValidated = validateUserByEmail(userDto);
        if (userValidated == null){
            User userSaved = repository.save(UserMapper.dtoToUser(userDto));
            return UserMapper.userToDto(userSaved);
        } else{
            throw new UserNotExistsException("Usuario con mail: " + userDto.getEmail() + " ya existe");
        }

    }


    public UserDto getUserById(Long id) {
        User entity = repository.findById(id).get();
        return UserMapper.userToDto(entity);
    }

    public String deleteUser(Long id) throws UserNotExistsException {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            throw new UserNotExistsException("El usuario a eliminar elegido no existe");
        }

    }

    public UserDto updateUser(Long id, UserDto dto) {
        if (repository.existsById(id)){
            User userToModify = repository.findById(id).get();

            if (dto.getFirstName() != null){
                userToModify.setFirstName(dto.getFirstName());
            }

            if (dto.getLastName() != null){
                userToModify.setLastName(dto.getLastName());
            }

            if (dto.getEmail() != null){
                userToModify.setEmail(dto.getEmail());
            }

            if (dto.getPassword() != null){
                userToModify.setPassword(dto.getPassword());
            }

            if (dto.getDocument() != null){
                userToModify.setDocument(dto.getDocument());
            }
            if (dto.getAddress() != null){
                userToModify.setAddress(dto.getAddress());
            }
            if (dto.getBirthDate() != null){
                userToModify.setBirthDate(dto.getBirthDate());
            }

            User userModified = repository.save(userToModify);

            return UserMapper.userToDto(userModified);
        }

        return null;
    }

    public User validateUserByEmail(UserDto dto){
        return repository.findByEmail(dto.getEmail());
    }
}