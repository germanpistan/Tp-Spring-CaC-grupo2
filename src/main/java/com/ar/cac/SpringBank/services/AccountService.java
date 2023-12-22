package com.ar.cac.SpringBank.services;


import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.exceptions.*;
import com.ar.cac.SpringBank.records.account.AccountRecord;
import com.ar.cac.SpringBank.records.account.NewAccountRecord;
import com.ar.cac.SpringBank.records.account.UpdateAccountRecord;
import com.ar.cac.SpringBank.repositories.AccountRepository;
import com.ar.cac.SpringBank.utils.WordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserService userService;


    public AccountService(AccountRepository repository) {

        this.repository = repository;
    }

    public List<AccountRecord> getAccounts() {

        return repository.findAll().stream()
                .map(AccountRecord::new)
                .toList();
    }

    protected Account getAccountByIdBase(Long id) throws AccountNotFoundException {


        return repository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountRecord getAccountById(Long id) throws AccountNotFoundException {


        return repository.findById(id)
                .map(AccountRecord::new)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountRecord getAccountByAlias(String alias) throws AccountNotFoundException {

        return repository.findByAlias(alias)
                .map(AccountRecord::new)
                .orElseThrow(AccountNotFoundException::new);
    }

    public AccountRecord createAccount(NewAccountRecord record) throws UserNotFoundException {

        var user = userService.getUserByIdBase(record.userId());
        String cbu = generatedCBU();
        String alias = generatedAlias();

        while (repository.existsByCbu(cbu))
            cbu = generatedCBU();

        while (repository.existsByAlias(alias))
            alias = generatedAlias();

        Account account = new Account(user, record.type(), cbu, alias);

        return new AccountRecord(
                repository.save(account)
        );
    }

    public void updateAlias(Long id, UpdateAccountRecord record) throws DuplicateAliasException, AccountNotFoundException {


        var account = getAccountByIdBase(id);
        checkDuplicateAlias(id, record.alias());

        account.update(record);

        repository.save(account);
    }

    public Account updateAmount(Long id, BigDecimal amount) throws AccountNotFoundException, InsufficientFoundsException {

        var account = repository.findById(id)
                .orElseThrow(AccountNotFoundException::new);

        if (amount.signum() < 0 && account.getAmount().compareTo(amount) < 0)
            throw new InsufficientFoundsException();

        account.updateAmount(amount);
        return repository.save(account);
    }

    public void deleteAccount(Long id) throws AccountNotFoundException {

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

    protected void checkExistsCbu(String cbu) throws DuplicateCbuException {
        var result = repository.existsByCbu(cbu);
        if (result) throw new DuplicateCbuException();
    }

    protected void checkDuplicateCbu(Long id, String cbu) throws DuplicateCbuException {
        var result = repository.existsByCbuAndIdNot(cbu, id);
        if (result) throw new DuplicateCbuException();
    }

    protected void checkExistsAlias(String alias) throws DuplicateAliasException {
        var result = repository.existsByAlias(alias);
        if (result) throw new DuplicateAliasException();
    }

    protected void checkDuplicateAlias(Long id, String alias) throws DuplicateAliasException {
        var result = repository.existsByAliasAndIdNot(alias, id);
        if (result) throw new DuplicateAliasException();

    }

    /**
     * Genera un CBU único para la cuenta
     *
     * @return
     */
    private String generatedCBU() {

        /*
        La CBU está compuesta por 22 dígitos, separados en dos bloques.
        El primer bloque tiene un número de entidad de 3 dígitos, un número de sucursal de 4 dígitos y un dígito
        verificador, en este caso usamos el 3. El segundo bloque tiene un número de 13 dígitos que identifica la
        cuenta dentro de la entidad y la sucursal, más un dígito verificador, en este caso usamos el 9.
         */

        final String ENTITY = "999";
        final String BANK_BRANCH = "0001";
        final String CHECKER_ONE = "3";
        final String CHECKER_TWO = "9";

        long randomNumber = Math.abs(new Random().nextLong() % 1_000_000_000L);
        String accountNumber = String.format("%013d", randomNumber);

        StringBuilder sb = new StringBuilder();

        sb.append(ENTITY)
                .append(BANK_BRANCH)
                .append(CHECKER_ONE)
                .append(accountNumber)
                .append(CHECKER_TWO);

        return sb.toString();
    }

    private String generatedAlias() {
        WordGenerator wg = new WordGenerator();
        StringBuilder sb = new StringBuilder();

        sb.append(wg.getRandomWord())
                .append(".")
                .append(wg.getRandomWord())
                .append(".")
                .append(wg.getRandomWord());

        return sb.toString()
                .toUpperCase();
    }

    public void adjustment(Long id, BigDecimal amount) throws AccountNotFoundException {

        var account = getAccountByIdBase(id);
        account.updateAmount(amount);
        repository.save(account);
    }
}
