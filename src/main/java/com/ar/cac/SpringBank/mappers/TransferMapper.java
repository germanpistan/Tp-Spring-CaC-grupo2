package com.ar.cac.SpringBank.mappers;

import com.ar.cac.SpringBank.entities.Transfer;
import com.ar.cac.SpringBank.entities.dtos.TransferDto;

public class TransferMapper {
    public static Transfer dtoToTransfer(TransferDto dto) {
        return Transfer.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                //.date(dto.getDate())
                .sourceAccountId(dto.getSourceAccountId())
                .targetAccountId(dto.getTargetAccountId())
                .build();

    }


    public static TransferDto transferToDto(Transfer transfer) {

        return TransferDto.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                //.date(transfer.getDate())
                .sourceAccountId(transfer.getSourceAccountId())
                .targetAccountId(transfer.getTargetAccountId())
                .build();
    }
}