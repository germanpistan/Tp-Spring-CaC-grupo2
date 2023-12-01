package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.UserDto;
import com.ar.cac.SpringBank.mappers.UserMapper;
import com.ar.cac.SpringBank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    public List<UserDto> getUsers() {

        /*List<User> users = repository.findAll();
        List<UserDto> usersDtos = users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());*/
        return repository.findAll().stream().map(UserMapper::userToDto).toList();
    }

    public UserDto createUser(UserDto userDto) throws DuplicateEmailException {

        checkExistEmail(userDto);

        // TODO: Falta aplicar excepciones de validación

        User userSaved = repository.save(UserMapper.dtoToUser(userDto));

        return UserMapper.userToDto(userSaved);
    }


    public UserDto getUserById(Long id) throws UserNotFoundException {

        // Refactor
        /*User entity = repository.findById(id).get();
        return UserMapper.userToDto(entity);*/

        return repository.findById(id)
                .map(UserMapper::userToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    // Se modifica a void
    public void deleteUser(Long id) throws UserNotFoundException {

        // Refactor
        /*if (repository.existsById(id)) {
            repository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            throw new UserNotFoundException();
        }*/

        checkExistUser(id);
        repository.deleteById(id);
    }

    public UserDto updateUser(Long id, UserDto dto) {

        // TODO: Falta implementar excepciones de validación.

        if (repository.existsById(id)) {
            User userToModify = repository.findById(id).get();

            if (dto.getFirstName() != null) {
                userToModify.setFirstName(dto.getFirstName());
            }

            if (dto.getLastName() != null) {
                userToModify.setLastName(dto.getLastName());
            }

            if (dto.getEmail() != null) {
                userToModify.setEmail(dto.getEmail());
            }

            if (dto.getPassword() != null) {
                userToModify.setPassword(dto.getPassword());
            }

            if (dto.getDocument() != null) {
                userToModify.setDocument(dto.getDocument());
            }
            if (dto.getAddress() != null) {
                userToModify.setAddress(dto.getAddress());
            }
            if (dto.getBirthDate() != null) {
                userToModify.setBirthDate(dto.getBirthDate());
            }

            User userModified = repository.save(userToModify);

            return UserMapper.userToDto(userModified);
        }

        return null;
    }

    protected void checkExistUser(Long id) throws UserNotFoundException {

        var result = repository.existsById(id);
        if (result) throw new UserNotFoundException();
    }

    protected void checkExistEmail(UserDto dto) throws DuplicateEmailException {

        var result = repository.existsByEmail(dto.getEmail());
        if (result) throw new DuplicateEmailException();
    }
}