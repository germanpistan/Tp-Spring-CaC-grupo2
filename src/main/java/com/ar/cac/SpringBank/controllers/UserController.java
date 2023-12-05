package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.Exceptions.DuplicateDocumentException;
import com.ar.cac.SpringBank.Exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.entities.dtos.UserDto;
import com.ar.cac.SpringBank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDto>> getUsers() {

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
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {

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
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto user) {

        // TODO: Faltan validaciones

        try {

            service.updateUser(id, user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (DuplicateEmailException | DuplicateDocumentException e) {

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
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {


        try {

            service.deleteUser(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}