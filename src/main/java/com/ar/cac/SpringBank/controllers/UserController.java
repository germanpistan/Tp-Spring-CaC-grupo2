package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.Exceptions.UserNotExistsException;
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

    public UserController(UserService service){
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> lista = service.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto user){
        try {
            return  ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
        } catch (UserNotExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, user));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
        } catch (UserNotExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}