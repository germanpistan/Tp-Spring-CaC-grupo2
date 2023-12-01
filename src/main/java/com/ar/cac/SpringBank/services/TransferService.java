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

import java.time.LocalDate;
import java.util.Date;
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


        accountService.checkExistAccount(dto.getOrigin());
        accountService.checkAmount(dto.getOrigin(), dto.getAmount());
        accountService.checkExistAccount(dto.getTarget());

        var entity = repository.save(TransferMapper.dtoToTransfer(dto));

        return TransferMapper.transferToDto(entity);
    }

    //TODO ESTO LO AGREGO DEL REPO DE LA MAÑANA, PARA QUE REVISEMOS. ME PARECE QUE ASI ES LA FORMA DE COMPROBACION
    @Transactional
    public TransferDto performTransfer(TransferDto dto) throws AccountNotFoundException, InsufficientFoundsException {
        // Comprobar si las cuentas de origen y destino existen
        Account originAccount = accountRepository.findById(dto.getOrigin())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getOrigin()));
        Account destinationAccount = accountRepository.findById(dto.getTarget())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getTarget()));

        // Comprobar si la cuenta de origen tiene fondos suficientes
        if (originAccount.getAmount().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException("Insufficient funds in the account with id: " + dto.getOrigin());
        }

        // Realizar la transferencia
        originAccount.setAmount(originAccount.getAmount().subtract(dto.getAmount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(dto.getAmount()));

        // Guardar las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        // Crear la transferencia y guardarla en la base de datos
        Transfer transfer = new Transfer();
        // Creamos un objeto del tipo Date para obtener la fecha actual
        Date date = new Date();
        // Seteamos el objeto fecha actual en el transferDto
        transfer.setDate(date);
        transfer.setOrigin(originAccount.getId());
        transfer.setTarget(destinationAccount.getId());
        transfer.setAmount(dto.getAmount());
        transfer = repository.save(transfer);

        // Devolver el DTO de la transferencia realizada
        return TransferMapper.transferToDto(transfer);
    }
}
