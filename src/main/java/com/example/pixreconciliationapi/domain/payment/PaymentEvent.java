package com.example.pixreconciliationapi.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentEvent {

    private final UUID idPayment;
    private final BigDecimal value;
    private final String externalId;

    public PaymentEvent(UUID idPayment, BigDecimal value, String externalId) {

        if(idPayment == null){
            throw new IllegalArgumentException("O id do pagamento nao pode ser nulo"); }
        if(value == null){
            throw new IllegalArgumentException("O valor do pagamento nao pode ser nulo"); }

        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor do pagamento nao pode ser negativo");
        }
        if(externalId == null){
            throw new IllegalArgumentException("O id externo do pagamento nao pode ser nulo");
        }
        if(externalId.length()== 0){
            throw new IllegalArgumentException("O id externo do pagamento nao pode ser nulo");
        }

        this.idPayment = idPayment;
        this.value = value;
        this.externalId = externalId;

    }

    public UUID getIdPayment() {
        return idPayment;
    }

    public String getExternalId() {
        return externalId;
    }

    public BigDecimal getValue() {
        return value;
    }
}
