package com.ar.cac.SpringBank;

import com.ar.cac.SpringBank.entities.enums.AccountType;
import com.ar.cac.SpringBank.exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.exceptions.DuplicateDocumentException;
import com.ar.cac.SpringBank.exceptions.DuplicateEmailException;
import com.ar.cac.SpringBank.exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.records.account.NewAccountRecord;
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
        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }

    private void adjustment() throws AccountNotFoundException {
        accountService.adjustment(1L, BigDecimal.valueOf(5000));
        accountService.adjustment(2L, BigDecimal.valueOf(5000));
        accountService.adjustment(3L, BigDecimal.valueOf(5000));
        accountService.adjustment(4L, BigDecimal.valueOf(5000));
        accountService.adjustment(5L, BigDecimal.valueOf(5000));
        accountService.adjustment(6L, BigDecimal.valueOf(5000));
    }

    private void seedAccounts() throws UserNotFoundException, AccountNotFoundException {

        accountService.createAccount(
                new NewAccountRecord(1L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(1L, AccountType.CAJA_AHORRO_DOLAR));
        accountService.createAccount(
                new NewAccountRecord(2L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(2L, AccountType.CAJA_AHORRO_DOLAR));
        accountService.createAccount(
                new NewAccountRecord(3L, AccountType.CAJA_AHORRO_PESOS));
        accountService.createAccount(
                new NewAccountRecord(3L, AccountType.CAJA_AHORRO_DOLAR));


        adjustment();
    }

    private void seedUsers() throws DuplicateEmailException, DuplicateDocumentException {
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
