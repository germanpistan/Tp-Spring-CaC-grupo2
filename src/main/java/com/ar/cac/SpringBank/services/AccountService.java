package com.ar.cac.SpringBank.services;


import com.ar.cac.SpringBank.Exceptions.*;

import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.User;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;


import com.ar.cac.SpringBank.mappers.AccountMapper;


import com.ar.cac.SpringBank.entities.dtos.UserDto;
import com.ar.cac.SpringBank.mappers.AccountMapper;

import com.ar.cac.SpringBank.mappers.UserMapper;

import com.ar.cac.SpringBank.repositories.AccountRepository;
import com.ar.cac.SpringBank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;





    @Autowired
    private UserService userService;




    public AccountService(AccountRepository repository) {

        this.repository = repository;

    }

    public List<AccountDto> getAccounts() {

        return repository.findAll().stream()
                .map(AccountMapper::accountToDto)
                .toList();
    }

    public AccountDto getAccountById(Long id) throws AccountNotFoundException {


        return repository.findById(id)
                .map(AccountMapper::accountToDto)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountDto createAccount(AccountDto dto) throws UserNotFoundException, DuplicateCbuException, DuplicateAliasException {

        checkExistsCbu(dto.getCbu());
        checkExistsAlias(dto.getAlias());

        Account accountSaved = repository.save(AccountMapper.dtoToAccount(dto));



        checkExistsCbu(dto.getCbu());
        checkExistsAlias(dto.getAlias());
        UserDto user= userService.getUserById(dto.getUserId());


        Account accountSaved = repository.save(AccountMapper.dtoToAccount(dto,UserMapper.dtoToUser(user)));

        return AccountMapper.accountToDto(accountSaved);
    }

    public void updateAccount (Long id, AccountDto dto) throws DuplicateAliasException, DuplicateCbuException, AccountNotFoundException, UserNotFoundException {


            var acc = getAccountById(id);

            var user = userService.getUserById(id);


            if (dto.getAlias() != null) {

                checkDuplicateAlias(dto.getId(), dto.getAlias());
                acc.setAlias(dto.getAlias());

            }

            if (dto.getAmount() != null) {
                acc.setAmount(dto.getAmount());
            }

            if (dto.getType() != null) {
                acc.setType(dto.getType());
            }

            if (dto.getCbu() != null) {
                checkDuplicateCbu(dto.getId(), dto.getCbu());
                acc.setCbu(dto.getCbu());
            }


            Account accountModified = repository.save(AccountMapper.dtoToAccount(acc));

            Account accountModified = repository.save(AccountMapper.dtoToAccount(acc,UserMapper.dtoToUser(user)));


    }


    public void deleteAccount (Long id) throws AccountNotFoundException {

        checkExistAccount(id);
        repository.deleteById(id);

    }

    protected void checkExistAccount(Long id) throws AccountNotFoundException {

        var result = repository.existsById(id);
        if (!result) throw new AccountNotFoundException();
    }

    protected void checkAmount(Long id, BigDecimal amount) throws InsufficientFoundsException {

        var result = repository.existsByIdAndAmountGreaterThanEqual(id, amount);
        if (result) throw new InsufficientFoundsException();
    }

    protected void checkExistsCbu( String cbu) throws DuplicateCbuException {
        var result= repository.existsByCbu(cbu);
        if(result) throw new DuplicateCbuException();
    }

    protected void checkDuplicateCbu(Long id, String cbu) throws DuplicateCbuException {
        var result= repository.existsByCbuAndIdNot(cbu, id);
        if (result) throw new DuplicateCbuException();
    }

    protected void checkExistsAlias(String alias) throws DuplicateAliasException {
        var result= repository.existsByAlias(alias);
        if(result) throw new DuplicateAliasException();
    }

    protected void checkDuplicateAlias(Long id, String alias) throws DuplicateAliasException {
        var result= repository.existsByAliasAndIdNot(alias,id);
        if (result) throw new DuplicateAliasException();

        }
}
