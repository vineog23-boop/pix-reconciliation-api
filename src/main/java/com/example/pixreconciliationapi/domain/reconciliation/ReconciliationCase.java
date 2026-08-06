package com.example.pixreconciliationapi.domain.reconciliation;

import java.util.UUID;

public class ReconciliationCase {

    // Identificador interno do caso de conciliação.
    private final UUID reconciliationCaseId;

    // Identifica o estabelecimento dono do caso.
    private final UUID merchantId;

    // Identifica o valor que o estabelecimento esperava receber.
    private final UUID receivableId;

    // Identifica o evento de pagamento que será analisado.
    private final UUID paymentEventId;

    private ReconciliationStatus status;

    public ReconciliationCase(
            UUID reconciliationCaseId,
            UUID merchantId,
            UUID receivableId,
            UUID paymentEventId
    ) {
        if (reconciliationCaseId == null) {
            throw new IllegalArgumentException("O id do caso de conciliacao nao pode ser nulo");
        }

        if (merchantId == null) {
            throw new IllegalArgumentException("O id do estabelecimento nao pode ser nulo");
        }

        if (receivableId == null) {
            throw new IllegalArgumentException("O id do recebivel nao pode ser nulo");
        }

        if (paymentEventId == null) {
            throw new IllegalArgumentException("O id do evento de pagamento nao pode ser nulo");
        }

        this.reconciliationCaseId = reconciliationCaseId;
        this.merchantId = merchantId;
        this.receivableId = receivableId;
        this.paymentEventId = paymentEventId;
        this.status = ReconciliationStatus.PENDING;
    }

    public UUID getReconciliationCaseId() {
        return reconciliationCaseId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public UUID getReceivableId() {
        return receivableId;
    }

    public UUID getPaymentEventId() {
        return paymentEventId;
    }

    public ReconciliationStatus getStatus() {
        return status;
    }
}
