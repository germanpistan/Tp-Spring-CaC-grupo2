package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.DuplicateAliasException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.records.account.NewAccountRecord;
import com.ar.cac.SpringBank.records.account.UpdateAccountRecord;
import com.ar.cac.SpringBank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private final AccountService service;

    private AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAccounts() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAccounts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {

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
    public ResponseEntity<?> createAccount(@RequestBody NewAccountRecord record) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createAccount(record));

        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountRecord record) {

        try {

            service.updateAlias(id, record);

            return ResponseEntity
                    .noContent()
                    .build();
        } catch (DuplicateAliasException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (AccountNotFoundException e2) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e2.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {

        try {

            service.deleteAccount(id);

            return ResponseEntity
                    .noContent()
                    .build();
        } catch (AccountNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
