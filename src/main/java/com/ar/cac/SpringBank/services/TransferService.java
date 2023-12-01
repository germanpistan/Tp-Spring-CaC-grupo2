package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.AccountNotFoundException;
import com.ar.cac.SpringBank.Exceptions.InsufficientFoundsException;
import com.ar.cac.SpringBank.Exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.entities.dtos.TransferDto;
import com.ar.cac.SpringBank.mappers.TransferMapper;
import com.ar.cac.SpringBank.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private AccountService accountService;


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
}
