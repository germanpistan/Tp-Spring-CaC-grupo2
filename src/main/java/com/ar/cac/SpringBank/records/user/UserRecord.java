package com.ar.cac.SpringBank.records.user;

import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.records.account.AccountRecord;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record UserRecord(
        Long id,
        String lastName,
        String firstName,
        String email,
        String password,
        String document,
        String address,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        List<AccountRecord> accounts
) {

    public UserRecord(User user) {

        this(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.getDocument(),
                user.getAddress(),
                user.getBirthDate(),
                new ArrayList<>(
                        user.getAccounts()
                                .stream()
                                .map(AccountRecord::new)
                                .toList()
                )
        );
    }
}
