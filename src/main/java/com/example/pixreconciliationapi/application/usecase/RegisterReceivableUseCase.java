package com.example.pixreconciliationapi.application.usecase;

import com.example.pixreconciliationapi.application.port.ReceivableRepository;
import com.example.pixreconciliationapi.domain.receivable.Receivable;

import java.math.BigDecimal;
import java.util.UUID;

public class RegisterReceivableUseCase {

    private final ReceivableRepository receivableRepository;

    public RegisterReceivableUseCase(ReceivableRepository receivableRepository) {
        if (receivableRepository == null) {
            throw new IllegalArgumentException("O repositorio de recebiveis nao pode ser nulo");
        }

        this.receivableRepository = receivableRepository;
    }

    public Receivable execute(
            UUID merchantId,
            String txid,
            BigDecimal expectedAmount
    ) {
        UUID receivableId = UUID.randomUUID();

        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                txid,
                expectedAmount
        );

        receivableRepository.save(receivable);

        return receivable;
    }
}
