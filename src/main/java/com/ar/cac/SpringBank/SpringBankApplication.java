package com.ar.cac.SpringBank;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import com.ar.cac.SpringBank.exceptions.*;
import com.ar.cac.SpringBank.records.account.NewAccountRecord;
import com.ar.cac.SpringBank.records.transfer.NewTransferRecord;
import com.ar.cac.SpringBank.records.user.NewUserRecord;
import com.ar.cac.SpringBank.services.AccountService;
import com.ar.cac.SpringBank.services.TransferService;
import com.ar.cac.SpringBank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class SpringBankApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {

            seedUsers();
            seedAccounts();
            seedTransfers();
        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }

    private void seedTransfers() throws AccountNotFoundException, InsufficientFundsException, IncompatibleAccountTypeException {

        transferService.createTransfer(
                new NewTransferRecord(1L, 4L, BigDecimal.valueOf(10000))
        );
        transferService.createTransfer(
                new NewTransferRecord(3L, 5L, BigDecimal.valueOf(25))
        );
        transferService.createTransfer(
                new NewTransferRecord(1L, 6L, BigDecimal.valueOf(10000))
        );
        transferService.createTransfer(
                new NewTransferRecord(3L, 7L, BigDecimal.valueOf(25))
        );
        transferService.createTransfer(
                new NewTransferRecord(1L, 8L, BigDecimal.valueOf(10000))
        );
        transferService.createTransfer(
                new NewTransferRecord(3L, 9L, BigDecimal.valueOf(25))
        );
    }

    private void seedAccounts() throws UserNotFoundException, AccountNotFoundException, DuplicateAliasException {

        accountService.initBank();

        accountService.createAccount(
                new NewAccountRecord(2L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(2L, AccountType.CAJA_AHORRO_DOLAR));
        accountService.createAccount(
                new NewAccountRecord(3L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(3L, AccountType.CAJA_AHORRO_DOLAR));
        accountService.createAccount(
                new NewAccountRecord(4L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(4L, AccountType.CAJA_AHORRO_DOLAR));
    }

    private void seedUsers() throws DuplicateEmailException, DuplicateDocumentException {

        userService.initBank();

        userService.createUser(
                new NewUserRecord(
                        "bazan",
                        "facundo",
                        "facundobazan@gmail.com",
                        "A@alvlgeddl1",
                        "33000001",
                        "los sauces sn",
                        LocalDate.parse("2001-01-01")
                )
        );

        userService.createUser(
                new NewUserRecord(
                        "pistan",
                        "german",
                        "germanpistan@gmail.com",
                        "A@alvlgeddl2",
                        "33000002",
                        "los robles sn",
                        LocalDate.parse("2002-02-02")
                )
        );

        userService.createUser(
                new NewUserRecord(
                        "ciribe",
                        "nicolas",
                        "nicolasciribe@gmail.com",
                        "A@alvlgeddl3",
                        "33000003",
                        "los alamos sn",
                        LocalDate.parse("2003-03-03")
                )
        );
    }
}
