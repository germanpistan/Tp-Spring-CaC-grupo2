package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.entities.Transfer;
import com.ar.cac.SpringBank.entities.enums.AccountType;
import com.ar.cac.SpringBank.exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.exceptions.IncompatibleAccountTypeException;
import com.ar.cac.SpringBank.exceptions.InsufficientFundsException;
import com.ar.cac.SpringBank.exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.records.transfer.NewTransferRecord;
import com.ar.cac.SpringBank.records.transfer.TransferRecord;
import com.ar.cac.SpringBank.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private AccountService accountService;

    public List<TransferRecord> getTransfers() {


        return repository.findAll().stream().map(TransferRecord::new).toList();
    }

    public Transfer getTransferByIdBase(Long id) throws TransferNotFoundException {


        return repository.findById(id)
                .orElseThrow(TransferNotFoundException::new);
    }

    public TransferRecord getTransferById(Long id) throws TransferNotFoundException {


        return repository.findById(id)
                .map(TransferRecord::new)
                .orElseThrow(TransferNotFoundException::new);
    }

    @Transactional
    public TransferRecord createTransfer(NewTransferRecord record) throws AccountNotFoundException, InsufficientFundsException, IncompatibleAccountTypeException {

        accountService.compareAccountType(record.accountId(), record.targetAccountId());

        var sourceAccount = accountService.updateAmount(record.accountId(), record.amount().negate());
        var targetAccount = accountService.updateAmount(record.targetAccountId(), record.amount());

        return new TransferRecord(
                repository.save(new Transfer(
                        sourceAccount,
                        targetAccount,
                        record.amount()
                ))
        );
    }

    @Transactional
    public void deleteTransfer(Long id) throws TransferNotFoundException, AccountNotFoundException, IncompatibleAccountTypeException, InsufficientFundsException {

        var transfer = getTransferById(id);
        var account = accountService.getAccountById(transfer.accountId());

        if (account.type().equals(AccountType.CAJA_AHORRO_DOLAR)) {
            createTransfer(new NewTransferRecord(1L, id, transfer.amount()));
        } else {
            createTransfer(new NewTransferRecord(3L, id, transfer.amount()));
        }
    }
}
