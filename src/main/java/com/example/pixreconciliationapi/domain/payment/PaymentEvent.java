package com.example.pixreconciliationapi.domain.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentEvent {

    private final UUID idPayment;
    private final BigDecimal value;
    private final String externalId;
    private final UUID txId;

    public PaymentEvent(UUID idPayment, BigDecimal value, String externalId, UUID txId) {
        if (idPayment == null) {
            throw new IllegalArgumentException(
                    "O id do pagamento nao pode ser nulo"
            );
        }

        if (value == null) {
            throw new IllegalArgumentException(
                    "O valor do pagamento nao pode ser nulo"
            );
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor do pagamento deve ser maior que zero"
            );
        }

        if (externalId == null || externalId.isBlank()) {
            throw new IllegalArgumentException(
                    "O id externo do pagamento não pode ser vazio"
            );
        }

        if (txId == null) {
            throw new IllegalArgumentException(
                    "O txId nao pode ser nulo"
            );
        }

        this.idPayment = idPayment;
        this.value = value;
        this.externalId = externalId;
        this.txId = txId;
    }

    public UUID getIdPayment() {
        return idPayment;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getExternalId() {
        return externalId;
    }

    public UUID getTxId() {
        return txId;
    }
}
