package com.example.pixreconciliationapi.domain.reconciliation;

import com.example.pixreconciliationapi.domain.payment.PaymentEvent;
import com.example.pixreconciliationapi.domain.receivable.Receivable;

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

    public void reconcile(Receivable receivable, PaymentEvent paymentEvent) {
        if (receivable == null) {
            throw new IllegalArgumentException("O recebivel nao pode ser nulo");
        }

        if (paymentEvent == null) {
            throw new IllegalArgumentException("O evento de pagamento nao pode ser nulo");
        }

        if (status != ReconciliationStatus.PENDING) {
            throw new IllegalStateException("O caso de conciliacao ja foi analisado");
        }

        if (!receivableId.equals(receivable.getReceivableId())) {
            throw new IllegalArgumentException("O recebivel nao pertence a este caso de conciliacao");
        }

        if (!paymentEventId.equals(paymentEvent.getPaymentEventId())) {
            throw new IllegalArgumentException("O evento de pagamento nao pertence a este caso de conciliacao");
        }

        if (!merchantId.equals(receivable.getMerchantId())
                || !merchantId.equals(paymentEvent.getMerchantId())) {
            throw new IllegalArgumentException("Os dados nao pertencem ao mesmo estabelecimento");
        }

        if (!receivable.getTxid().equals(paymentEvent.getTxid())) {
            status = ReconciliationStatus.REJECTED;
            return;
        }

        receivable.registerPayment(paymentEvent.getAmountReceived());
        status = ReconciliationStatus.MATCHED;
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
