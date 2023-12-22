package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.exceptions.DuplicateDocumentException;
import com.ar.cac.SpringBank.exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.exceptions.validations.EmailFormatException;
import com.ar.cac.SpringBank.records.user.NewUserRecord;
import com.ar.cac.SpringBank.records.user.UpdateUserRecord;
import com.ar.cac.SpringBank.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<?>> getUsers() {

        // Refactor
        // List<UserDto> lista = service.getUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getUserById(id));
        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserRecord user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            bindingResult
                                    .getFieldErrors()
                                    .stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .toList()
                    );
        }

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createUser(user));

        } catch (DuplicateEmailException | DuplicateDocumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRecord record) {

        try {

            service.updateUser(id, record);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (DuplicateEmailException | DuplicateDocumentException | EmailFormatException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (UserNotFoundException e2) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e2.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        try {

            service.disableUser(id);
            return (ResponseEntity
                    .status(HttpStatus.OK)
                    .build());
        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}