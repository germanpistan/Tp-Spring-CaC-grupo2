package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.DuplicateDocumentException;
import com.ar.cac.SpringBank.Exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.Exceptions.validations.EmailFormatException;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.records.user.NewUserRecord;
import com.ar.cac.SpringBank.records.user.UpdateUserRecord;
import com.ar.cac.SpringBank.records.user.UserRecord;
import com.ar.cac.SpringBank.repositories.UserRepository;
import com.ar.cac.SpringBank.utils.Validations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    /*public List<UserDto> getUsers() {

        return repository.findAll().stream()
                .map(UserMapper::userToDto)
                .toList();
    }*/

    public List<UserRecord> getUsers() {

        return repository.findAll().stream()
                .filter(User::isEnabled)
                .map(UserRecord::new)
                .toList();
    }

    /*public UserDto getUserById(Long id) throws UserNotFoundException {

        return repository.findById(id)
                .map(UserMapper::userToDto)
                .orElseThrow(UserNotFoundException::new);
    }*/

    public UserRecord getUserById(Long id) throws UserNotFoundException {

        return repository.findById(id)
                .filter(User::isEnabled)
                .map(UserRecord::new)
                .orElseThrow(UserNotFoundException::new);
    }

    /*public UserDto createUser(UserDto userDto) throws DuplicateEmailException, DuplicateDocumentException {

        checkExistEmail(userDto.getEmail());
        checkExistDocument(userDto.getDocument());

        User userSaved = repository.save(UserMapper.dtoToUser(userDto));

        return UserMapper.userToDto(userSaved);
    }*/

    public UserRecord createUser(NewUserRecord record) throws DuplicateEmailException, DuplicateDocumentException {

        checkExistEmail(record.email());
        checkExistDocument(record.document());

        return new UserRecord(
                repository.save(
                        new User(record)
                )
        );
    }


    /*public void updateUser(Long id, UserDto dto) throws DuplicateEmailException, DuplicateDocumentException, UserNotFoundException {

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
    }*/

    public void updateUser(Long id, UpdateUserRecord record) throws DuplicateEmailException, DuplicateDocumentException, UserNotFoundException, EmailFormatException {

        var user = repository.findById(id).orElseThrow(UserNotFoundException::new);

        if (record.email() != null) {
            checkDuplicateEmail(record.email(), id);
            Validations.emailValidator(record.email());
        }
        if (record.document() != null) checkDuplicateDocument(record.document(), id);

        //user.setEmail("test@mail.com");
        user.update(record);
    }

    public void disableUser(Long id) throws UserNotFoundException {

        var user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setEnabled(!user.isEnabled());

        repository.save(user);
    }


    protected void checkExistUser(Long id) throws UserNotFoundException {

        var result = repository.existsById(id);
        if (result) throw new UserNotFoundException();
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