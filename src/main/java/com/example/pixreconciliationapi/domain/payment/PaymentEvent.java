package com.example.pixreconciliationapi.domain.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PaymentEvent {

    // Identificador interno do evento em nossa aplicação.
    private final UUID paymentEventId;

    // Identifica o estabelecimento que é dono deste pagamento.
    private final UUID merchantId;

    // Identifica o evento na origem e permite reconhecer reenvios do provedor.
    private final String providerEventId;

    // Identifica a cobrança que esperava receber o pagamento.
    private final String txid;

    // Identifica a transferência Pix que efetivamente aconteceu.
    private final String endToEndId;

    private final BigDecimal amountReceived;

    // Guarda data, hora e deslocamento de fuso do recebimento.
    private final OffsetDateTime receivedAt;

    // Referência informada para ajudar a reconhecer o pagador.
    private final String payerReference;

    // Informa qual provedor enviou o evento de pagamento.
    private final PaymentProvider provider;

    public PaymentEvent(
            UUID paymentEventId,
            UUID merchantId,
            String providerEventId,
            String txid,
            String endToEndId,
            BigDecimal amountReceived,
            OffsetDateTime receivedAt,
            String payerReference,
            PaymentProvider provider
    ) {
        if (paymentEventId == null) {
            throw new IllegalArgumentException("O id do evento de pagamento nao pode ser nulo");
        }

        if (merchantId == null) {
            throw new IllegalArgumentException("O id do estabelecimento nao pode ser nulo");
        }

        if (providerEventId == null || providerEventId.isBlank()) {
            throw new IllegalArgumentException("O id do evento no provedor nao pode ser vazio");
        }

        if (txid == null || txid.isBlank()) {
            throw new IllegalArgumentException("O txid nao pode ser vazio");
        }

        if (endToEndId == null || endToEndId.isBlank()) {
            throw new IllegalArgumentException("O endToEndId nao pode ser vazio");
        }

        if (amountReceived == null) {
            throw new IllegalArgumentException("O valor do pagamento nao pode ser nulo");
        }

        if (amountReceived.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero");
        }

        if (receivedAt == null) {
            throw new IllegalArgumentException("A data de recebimento nao pode ser nula");
        }

        if (payerReference == null || payerReference.isBlank()) {
            throw new IllegalArgumentException("A referencia do pagador nao pode ser vazia");
        }

        if (provider == null) {
            throw new IllegalArgumentException("O provedor nao pode ser nulo");
        }

        this.paymentEventId = paymentEventId;
        this.merchantId = merchantId;
        this.providerEventId = providerEventId;
        this.txid = txid;
        this.endToEndId = endToEndId;
        this.amountReceived = amountReceived;
        this.receivedAt = receivedAt;
        this.payerReference = payerReference;
        this.provider = provider;
    }

    public UUID getPaymentEventId() {
        return paymentEventId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public String getProviderEventId() {
        return providerEventId;
    }

    public String getTxid() {
        return txid;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public OffsetDateTime getReceivedAt() {
        return receivedAt;
    }

    public String getPayerReference() {
        return payerReference;
    }

    public PaymentProvider getProvider() {
        return provider;
    }
}
