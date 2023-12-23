package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.exceptions.DuplicateDocumentException;
import com.ar.cac.SpringBank.exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.exceptions.validations.EmailFormatException;
import com.ar.cac.SpringBank.records.user.NewUserRecord;
import com.ar.cac.SpringBank.records.user.UpdateUserRecord;
import com.ar.cac.SpringBank.records.user.UserRecord;
import com.ar.cac.SpringBank.repositories.UserRepository;
import com.ar.cac.SpringBank.utils.Validations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    public List<UserRecord> getUsers() {

        return repository.findAll().stream()
                .filter(User::isEnabled)
                .map(UserRecord::new)
                .toList();
    }

    public UserRecord getUserById(Long id) throws UserNotFoundException {

        return repository.findById(id)
                .filter(User::isEnabled)
                .map(UserRecord::new)
                .orElseThrow(UserNotFoundException::new);
    }

    protected User getUserByIdBase(Long id) throws UserNotFoundException {

        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserRecord createUser(NewUserRecord record) throws DuplicateEmailException, DuplicateDocumentException {

        checkExistEmail(record.email());
        checkExistDocument(record.document());

        return new UserRecord(
                repository.save(
                        new User(record)
                )
        );
    }

    public void updateUser(Long id, UpdateUserRecord record) throws DuplicateEmailException, DuplicateDocumentException, UserNotFoundException, EmailFormatException {

        var user = repository.findById(id).orElseThrow(UserNotFoundException::new);

        if (record.email() != null) {
            checkDuplicateEmail(record.email(), id);
            Validations.emailValidator(record.email());
        }
        if (record.document() != null) checkDuplicateDocument(record.document(), id);

        user.update(record);
    }

    public void disableUser(Long id) throws UserNotFoundException {

        var user = getUserByIdBase(id);
        user.disabled();

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

    public void initBank() {

        var user = new User(
                null,
                "SA",
                "SPRING BANK",
                "INFO@SPRINGBANK.COM",
                "BE9d&&Didpc5",
                "99999999",
                LocalDate.parse("2023-12-01"),
                "AV. CONSTITUCION 1950",
                null,
                null,
                false,
                new ArrayList<Account>()
        );

        repository.save(user);
    }
}