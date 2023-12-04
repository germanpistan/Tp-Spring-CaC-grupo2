package com.ar.cac.SpringBank.services;


import com.ar.cac.SpringBank.Exceptions.*;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import com.ar.cac.SpringBank.mappers.AccountMapper;
import com.ar.cac.SpringBank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

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


        return repository.findById(id)
                .map(AccountMapper::accountToDto)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountDto createAccount(AccountDto dto) throws UserNotFoundException, DuplicateCbuException, DuplicateAliasException {

        userService.checkExistUser(dto.getOwnerId());
        checkExistsCbu(dto.getCbu());
        checkExistsAlias(dto.getAlias());


        // TODO: Se debería realizar mediante una Transaction, se buscaría el usuario, se crearía la cuenta y posteriormente se actualizaría el usuario.
        //  Tener en cuenta que directamente se puede realizar un update del usuario con la nueva cuenta. Se aceptan sugerencias.

        Account accountSaved = repository.save(AccountMapper.dtoToAccount(dto));

        return AccountMapper.accountToDto(accountSaved);
    }

    public void updateAccount (Long id, AccountDto dto) throws DuplicateAliasException, DuplicateCbuException, AccountNotFoundException {


            var acc = getAccountById(id);

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

    }


    public void deleteAccount (Long id) throws AccountNotFoundException {

        checkExistAccount(id);
        repository.deleteById(id);

    }

    protected void checkExistAccount(Long id) throws AccountNotFoundException {

        var result = repository.existsById(id);
        if (result) throw new AccountNotFoundException();
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
