package com.ar.cac.SpringBank.controllers;


import com.ar.cac.SpringBank.exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.exceptions.IncompatibleAccountTypeException;
import com.ar.cac.SpringBank.exceptions.InsufficientFundsException;
import com.ar.cac.SpringBank.exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.records.transfer.NewTransferRecord;
import com.ar.cac.SpringBank.records.transfer.TransferRecord;
import com.ar.cac.SpringBank.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransferRecord>> getTransfers() {

        return ResponseEntity
                .ok(service.getTransfers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTransferById(@PathVariable Long id) {


        try {

            return ResponseEntity
                    .ok(service.getTransferById(id));
        } catch (TransferNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> performTransfer(@RequestBody NewTransferRecord record) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createTransfer(record));
        } catch (AccountNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (InsufficientFundsException | IncompatibleAccountTypeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTransfer(@PathVariable Long id) {


        try {

            service.deleteTransfer(id);

            return ResponseEntity
                    .noContent()
                    .build();
        } catch (TransferNotFoundException | AccountNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IncompatibleAccountTypeException | InsufficientFundsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
