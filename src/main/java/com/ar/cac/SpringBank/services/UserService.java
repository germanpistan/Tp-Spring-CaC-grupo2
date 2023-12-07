package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.DuplicateDocumentException;
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

        return repository.findAll().stream()
                .map(UserMapper::userToDto)
                .toList();
    }

    public UserDto getUserById(Long id) throws UserNotFoundException {

        return repository.findById(id)
                .map(UserMapper::userToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto createUser(UserDto userDto) throws DuplicateEmailException, DuplicateDocumentException {

        checkExistEmail(userDto.getEmail());
        checkExistDocument(userDto.getDocument());

        // TODO: Falta aplicar excepciones de validación

        User userSaved = repository.save(UserMapper.dtoToUser(userDto));

        return UserMapper.userToDto(userSaved);
    }


    public void updateUser(Long id, UserDto dto) throws DuplicateEmailException, DuplicateDocumentException, UserNotFoundException {

        var user = getUserById(id);

        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());

        if (dto.getLastName() != null) user.setLastName(dto.getLastName());

        if (dto.getEmail() != null) {

            checkDuplicateEmail(dto.getEmail(), dto.getId());
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) user.setPassword(dto.getPassword());

        if (dto.getDocument() != null) {

            checkDuplicateDocument(dto.getDocument(), dto.getId());
            user.setDocument(dto.getDocument());
        }

        if (dto.getAddress() != null) user.setAddress(dto.getAddress());

        if (dto.getBirthDate() != null) user.setBirthDate(dto.getBirthDate());


        User userModified = repository.save(UserMapper.dtoToUser(user));
    }

    public void disableUser(Long id) throws UserNotFoundException {

        if (repository.existsById(id)) {
            User user = repository.findById(id).get();
            user.setEnabled(false);
            repository.save(user);
        } else{
            throw new UserNotFoundException();
        }
    }

    protected void checkExistUser(Long id) throws UserNotFoundException {

        var result = repository.existsById(id);
        if (!result) throw new UserNotFoundException();
    }

    protected void checkExistEmail(String email) throws DuplicateEmailException {

        var result = repository.existsByEmail(email);
        if (result) throw new DuplicateEmailException();
    }

    protected void checkDuplicateEmail(String email, Long id) throws DuplicateEmailException {

        var result = repository.existsByEmailAndIdNot(email, id);
        if (result) throw new DuplicateEmailException();
    }

    protected void checkExistDocument(String document) throws DuplicateDocumentException {

        var result = repository.existsByDocument(document);
        if (result) throw new DuplicateDocumentException();
    }

    protected void checkDuplicateDocument(String document, Long id) throws DuplicateDocumentException {

        var result = repository.existsByDocumentAndIdNot(document, id);
        if (result) throw new DuplicateDocumentException();
    }
}