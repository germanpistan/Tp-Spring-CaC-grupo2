package com.ar.cac.SpringBank.mappers;

import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public static Account dtoToAccount(AccountDto dto){
        Account account= new Account();
        account.setAlias(dto.getAlias());
        account.setCbu(dto.getCbu());
        account.setType(dto.getType());
        account.setAmount(dto.getAmount());
        return account;

    }

    public static AccountDto accountToDto(Account account){
        AccountDto accountDto= new AccountDto();

        accountDto.setAlias(account.getAlias());
        accountDto.setCbu(account.getCbu());
        accountDto.setType(account.getType());
        accountDto.setAmount(account.getAmount());
        return accountDto;
    }
}
