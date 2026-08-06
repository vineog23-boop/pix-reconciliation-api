package com.example.pixreconciliationapi.domain.receivable;

import java.math.BigDecimal;
import java.util.UUID;

public class Receivable {

    // Identificador interno do recebível em nossa aplicação.
    private final UUID receivableId;

    // Identifica o estabelecimento que é dono desta cobrança.
    private final UUID merchantId;

    // Identifica a cobrança no contexto do Pix.
    private final String txid;

    private final BigDecimal expectedAmount;
    private BigDecimal receivedAmount;
    private ReceivableStatus status;

    public Receivable(
            UUID receivableId,
            UUID merchantId,
            String txid,
            BigDecimal expectedAmount
    ) {
        if (receivableId == null) {
            throw new IllegalArgumentException("O id do recebivel nao pode ser nulo");
        }

        if (merchantId == null) {
            throw new IllegalArgumentException("O id do estabelecimento nao pode ser nulo");
        }

        if (txid == null || txid.isBlank()) {
            throw new IllegalArgumentException("O txid nao pode ser vazio");
        }

        if (expectedAmount == null) {
            throw new IllegalArgumentException("O valor esperado nao pode ser nulo");
        }

        if (expectedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor esperado deve ser maior que zero");
        }

        this.receivableId = receivableId;
        this.merchantId = merchantId;
        this.txid = txid;
        this.expectedAmount = expectedAmount;
        this.receivedAmount = BigDecimal.ZERO;
        this.status = ReceivableStatus.OPEN;
    }

    public void registerPayment(BigDecimal paymentAmount) {
        if (paymentAmount == null) {
            throw new IllegalArgumentException("O valor recebido nao pode ser nulo");
        }

        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor recebido deve ser maior que zero");
        }

        BigDecimal updatedReceivedAmount = receivedAmount.add(paymentAmount);

        receivedAmount = updatedReceivedAmount;

        if (receivedAmount.compareTo(expectedAmount) < 0) {
            status = ReceivableStatus.PARTIALLY_PAID;
        } else if (receivedAmount.compareTo(expectedAmount) == 0) {
            status = ReceivableStatus.PAID;
        } else {
            status = ReceivableStatus.OVERPAID;
        }
    }

    public UUID getReceivableId() {
        return receivableId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public String getTxid() {
        return txid;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public ReceivableStatus getStatus() {
        return status;
    }
}
