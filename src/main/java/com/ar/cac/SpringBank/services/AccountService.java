package com.ar.cac.SpringBank.services;


import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.InsufficientFoundsException;
import com.ar.cac.SpringBank.Exceptions.UserNotFoundException;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import com.ar.cac.SpringBank.mappers.AccountMapper;
import com.ar.cac.SpringBank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository repository;

    @Autowired
    private final UserService userService;

    public AccountService(AccountRepository repository, UserService userService) {

        this.repository = repository;
        this.userService = userService;
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

        return repository.findById(id)
                .map(AccountMapper::accountToDto)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountDto createAccount(AccountDto dto) throws UserNotFoundException {

        userService.checkExistUser(dto.getOwnerId());

        // TODO: Se debería realizar mediante una Transaction, se buscaría el usuario, se crearía la cuenta y posteriormente se actualizaría el usuario.
        //  Tener en cuenta que directamente se puede realizar un update del usuario con la nueva cuenta. Se aceptan sugerencias.

        Account newAccount = AccountMapper.dtoToAccount(dto);
        var entity = repository.save(newAccount);
        return AccountMapper.accountToDto(entity);
    }

    public AccountDto updateAccount(Long id, AccountDto dto) {

        // TODO: Implementar Excepciones para cada error y devolverlas en el controller.

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

        // TODO: Falta implementar excepciones.
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
