package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.InsufficientFoundsException;
import com.ar.cac.SpringBank.Exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.Transfer;
import com.ar.cac.SpringBank.records.transfer.NewTransferRecord;
import com.ar.cac.SpringBank.records.transfer.TransferRecord;
import com.ar.cac.SpringBank.repositories.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;


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

    public TransferRecord createTransfer(NewTransferRecord record) throws AccountNotFoundException, InsufficientFoundsException {

        var sourceAccount = accountService.getAccountByIdBase(record.sourceAccountId());
        var targetAccount = accountService.getAccountByIdBase(record.sourceAccountId());
        accountService.checkAmount(record.sourceAccountId(), record.amount());

        var entity = repository.save(
                new Transfer(sourceAccount, targetAccount, record.amount())
        );

        return new TransferRecord(entity);
    }

    @Transactional
    public TransferRecord performTransfer(NewTransferRecord record) throws AccountNotFoundException, InsufficientFoundsException {

        Account sourceAcc = accountService.getAccountByIdBase(record.sourceAccountId());
        Account targetAcc = accountService.getAccountByIdBase(record.targetAccountId());

        if (sourceAcc.getAmount().compareTo(record.amount()) < 0)
            throw new InsufficientFoundsException();

        sourceAcc.updateAmount(record.amount().negate());
        targetAcc.updateAmount(record.amount());

        accountRepository.save(sourceAcc);
        accountRepository.save(targetAcc);

        return new TransferRecord(
                repository.save(new Transfer(
                        sourceAcc,
                        targetAcc,
                        record.amount()
                ))
        );
    }
}
