package com.ar.cac.SpringBank.mappers;

import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public Account dtoToAccount(AccountDto dto) {
        return Account.builder()
                .cbu(dto.getCbu())
                .id(dto.getId())
                .type(dto.getType())
                .amount(dto.getAmount())
                .alias(dto.getAlias())
                .owner(dto.getOwnerId())
                .build();
    }

    public static AccountDto accountToDto(Account account) {

        return AccountDto.builder()
                .cbu(account.getCbu())
                .id(account.getId())
                .type(account.getType())
                .amount(account.getAmount())
                .alias(account.getAlias())
                .ownerId(account.getOwner())
                .build();

    }
}
