package com.ar.cac.SpringBank.records.account;

import com.ar.cac.SpringBank.entities.Operation;
import com.ar.cac.SpringBank.entities.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationRecord(

        Long id,
        @Enumerated(EnumType.STRING)
        OperationType type,
        String sourceCBU,
        String targetCBU,
        BigDecimal amount,
        LocalDateTime createdAt
) {

    public OperationRecord(Operation operation) {

        this(
                operation.getId(),
                operation.getType(),
                operation.getAccount().getCbu(),
                operation.getTargetAccount().getCbu(),
                operation.getAmount(),
                operation.getCreatedAt()
        );
    }
}
