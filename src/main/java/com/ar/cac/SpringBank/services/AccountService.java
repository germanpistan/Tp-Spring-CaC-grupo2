package com.ar.cac.SpringBank.services;


import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.InsufficientFoundsException;
import com.ar.cac.SpringBank.Exceptions.UserNotExistsException;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import com.ar.cac.SpringBank.mappers.AccountMapper;
import com.ar.cac.SpringBank.mappers.UserMapper;
import com.ar.cac.SpringBank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<AccountDto> getAccounts() {

        return repository.findAll().stream()
                .map(AccountMapper::accountToDto)
                .toList();
    }

    public AccountDto getAccountById(Long id) throws AccountNotFoundException {

        /*Account acc = repository.findById(id).get();
        return AccountMapper.accountToDto(acc);*/
        // Refactor

        return repository.findById(id).map(AccountMapper::accountToDto).orElseThrow(AccountNotFoundException::new);
    }

    public AccountDto createAccount(AccountDto dto) {
        dto.setAmount(BigDecimal.ZERO);
        Account newAccount = AccountMapper.dtoToAccount(dto);
        return AccountMapper.accountToDto(repository.save(newAccount));
    }

    public AccountDto updateAccount(Long id, AccountDto dto) {
        if (repository.existsById(id)) {
            Account acc = repository.findById(id).get();

            if (dto.getAlias() != null) {
                acc.setAlias(dto.getAlias());
            }

            if (dto.getAmount() != null) {
                acc.setAmount(dto.getAmount());
            }

            if (dto.getType() != null) {
                acc.setType(dto.getType());
            }

            if (dto.getCbu() != null) {
                acc.setCbu(dto.getCbu());
            }
            return AccountMapper.accountToDto(acc);

        } else {

            return null;
        }
    }

    public String deleteAccount(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Cuenta eliminada";
        } else {
            return "No se pudo eliminar la cuenta";
        }
    }

    protected void checkExistAccount(Long id) throws AccountNotFoundException {

        var result = repository.existsById(id);
        if (result) throw new AccountNotFoundException();
    }

    protected void checkAmount(Long id, BigDecimal amount) throws InsufficientFoundsException {

        var result = repository.existsByIdAndAmountGreaterThanEqual(id, amount);
        if (result) throw new InsufficientFoundsException();
    }
}
