package com.example.pixreconciliationapi.domain.receivable;

import java.math.BigDecimal;
import java.util.UUID;

public class Receivable {

    private final UUID idReceivable;
    private final UUID idSale;
    private final UUID txId;
    private final BigDecimal expectedValue;

    public Receivable(UUID idReceivable, UUID idSale, UUID txId, BigDecimal expectedValue) {
        if (idReceivable == null) {
            throw new IllegalArgumentException(
                    "O id do recebivel nao pode ser nulo"
            );
        }

        if (idSale == null) {
            throw new IllegalArgumentException(
                    "O id da venda nao pode ser nulo"
            );
        }

        if (txId == null) {
            throw new IllegalArgumentException(
                    "O txId nao pode ser nulo"
            );
        }

        if (expectedValue == null) {
            throw new IllegalArgumentException(
                    "O valor esperado não pode ser nulo"
            );
        }

        if (expectedValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor esperado deve ser maior que zero"
            );
        }

        this.idReceivable = idReceivable;
        this.idSale = idSale;
        this.txId = txId;
        this.expectedValue = expectedValue;
    }

    public UUID getIdReceivable() {
        return idReceivable;
    }

    public UUID getIdSale() {
        return idSale;
    }

    public UUID getTxId() {
        return txId;
    }

    public BigDecimal getExpectedValue() {
        return expectedValue;
    }
}
