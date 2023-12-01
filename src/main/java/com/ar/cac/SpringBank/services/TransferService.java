package com.ar.cac.SpringBank.services;

import com.ar.cac.SpringBank.Exceptions.TransferNotFoundException;
import com.ar.cac.SpringBank.entities.Account;
import com.ar.cac.SpringBank.entities.Transfer;
import com.ar.cac.SpringBank.entities.dtos.AccountDto;
import com.ar.cac.SpringBank.entities.dtos.TransferDto;
import com.ar.cac.SpringBank.mappers.AccountMapper;
import com.ar.cac.SpringBank.mappers.TransferMapper;
import com.ar.cac.SpringBank.repositories.AccountRepository;
import com.ar.cac.SpringBank.repositories.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    private final TransferRepository repository;

    private final AccountRepository accountRepository;

    public TransferService(TransferRepository repository, AccountRepository accountRepository){
        this.repository=repository;
        this.accountRepository= accountRepository;
    }

   public List<TransferDto> getTransfers(){
        List<Transfer> transfers= repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());

   }

   public TransferDto getTransferById(Long id){
        Transfer transfer= repository.findById(id).orElseThrow(TransferNotFoundException::new);
        return TransferMapper.transferToDto(transfer);
   }

   public TransferDto createTransfer(TransferDto dto){


       return dto;
   }

}
