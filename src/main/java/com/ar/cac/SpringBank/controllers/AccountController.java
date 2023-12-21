package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.DuplicateAliasException;
import com.ar.cac.SpringBank.Exceptions.DuplicateCbuException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import com.ar.cac.SpringBank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private final AccountService service;

    private AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAccounts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) throws AccountNotFoundException {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.getAccountById(id));
        } catch (AccountNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDto account) throws DuplicateAliasException, DuplicateCbuException, UserNotFoundException {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createAccount(account));

        } catch (DuplicateCbuException | DuplicateAliasException | UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountDto account) throws AccountNotFoundException, UserNotFoundException, DuplicateCbuException, DuplicateAliasException {

        try {

            service.updateAccount(id, account);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (DuplicateCbuException | DuplicateAliasException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (AccountNotFoundException | UserNotFoundException e1) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e1.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) throws AccountNotFoundException {

        try {

            service.deleteAccount(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (AccountNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
