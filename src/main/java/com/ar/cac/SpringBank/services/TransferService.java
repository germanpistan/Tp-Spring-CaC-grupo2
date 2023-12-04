package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.InsufficientFoundsException;
import com.ar.cac.SpringBank.Exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.Transfer;
import com.ar.cac.SpringBank.entities.dtos.TransferDto;
import com.ar.cac.SpringBank.mappers.TransferMapper;
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


    public List<TransferDto> getTransfers() {

        // Refactor
        /*List<Transfer> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());*/

        return repository.findAll().stream().map(TransferMapper::transferToDto).toList();
    }

    public TransferDto getTransferById(Long id) throws TransferNotFoundException {

        /*Transfer transfer = repository.findById(id).orElseThrow(TransferNotFoundException::new);
        return TransferMapper.transferToDto(transfer);*/
        // Refactor

        return repository.findById(id).map(TransferMapper::transferToDto).orElseThrow(TransferNotFoundException::new);
    }


    public TransferDto createTransfer(TransferDto dto) throws AccountNotFoundException, InsufficientFoundsException {


        accountService.checkExistAccount(dto.getSourceAccountId());
        accountService.checkAmount(dto.getSourceAccountId(), dto.getAmount());
        accountService.checkExistAccount(dto.getTargetAccountId());

        var entity = repository.save(TransferMapper.dtoToTransfer(dto));

        return TransferMapper.transferToDto(entity);
    }


    @Transactional
    public TransferDto performTransfer(TransferDto dto) throws AccountNotFoundException, InsufficientFoundsException {
        // Comprobacion si las cuentas de origen y destino existen
        Account originAccount = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getSourceAccountId()));
        Account destinationAccount = accountRepository.findById(dto.getTargetAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getTargetAccountId()));

        // Comprobacion de los fondos de la cuenta origen
        if (originAccount.getAmount().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException("Insufficient funds in the account with id: " + dto.getSourceAccountId());
        }
        originAccount.setAmount(originAccount.getAmount().subtract(dto.getAmount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(dto.getAmount()));
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);
        Transfer transfer = new Transfer();
        transfer.setSourceAccountId(originAccount.getId());
        transfer.setTargetAccountId(destinationAccount.getId());
        transfer.setAmount(dto.getAmount());
        transfer = repository.save(transfer);
        return TransferMapper.transferToDto(transfer);
    }
}
