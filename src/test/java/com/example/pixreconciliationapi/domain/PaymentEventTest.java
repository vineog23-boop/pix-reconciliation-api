package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.payment.PaymentEvent;
import com.example.pixreconciliationapi.domain.payment.PaymentProvider;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentEventTest {

    @Test
    void shouldCreateValidEvent() {
        // Arrange
        UUID paymentEventId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        String providerEventId = "evt-demo-0001";
        String txid = "DEMO20260713000000000000000001";
        String endToEndId = "E00000000202607131730DEMO0001";
        BigDecimal amountReceived = new BigDecimal("42.00");
        OffsetDateTime receivedAt = OffsetDateTime.parse("2026-07-13T14:30:00-03:00");
        String payerReference = "cliente-demo-12";
        PaymentProvider provider = PaymentProvider.SIMULATED_PIX;

        // Act
        PaymentEvent paymentEvent = new PaymentEvent(
                paymentEventId,
                merchantId,
                providerEventId,
                txid,
                endToEndId,
                amountReceived,
                receivedAt,
                payerReference,
                provider
        );

        // Assert
        assertEquals(paymentEventId, paymentEvent.getPaymentEventId());
        assertEquals(merchantId, paymentEvent.getMerchantId());
        assertEquals(providerEventId, paymentEvent.getProviderEventId());
        assertEquals(txid, paymentEvent.getTxid());
        assertEquals(endToEndId, paymentEvent.getEndToEndId());
        assertEquals(amountReceived, paymentEvent.getAmountReceived());
        assertEquals(receivedAt, paymentEvent.getReceivedAt());
        assertEquals(payerReference, paymentEvent.getPayerReference());
        assertEquals(provider, paymentEvent.getProvider());
    }

    @Test
    void shouldRejectMissingIds() {
        // Arrange
        UUID paymentEventId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        BigDecimal amountReceived = new BigDecimal("42.00");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(null, merchantId, amountReceived));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(paymentEventId, null, amountReceived));
    }

    @Test
    void shouldRejectInvalidAmount() {
        // Arrange
        UUID paymentEventId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = new BigDecimal("-0.01");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(paymentEventId, merchantId, null));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(paymentEventId, merchantId, zeroAmount));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(paymentEventId, merchantId, negativeAmount));
    }

    @Test
    void shouldRejectMissingProviderData() {
        // Arrange
        String providerEventId = "event";
        String txid = "txid";
        String endToEndId = "endToEndId";
        String payerReference = "payer";
        OffsetDateTime receivedAt = OffsetDateTime.now();
        PaymentProvider provider = PaymentProvider.SIMULATED_PIX;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(null, txid, endToEndId, payerReference, receivedAt, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(" ", txid, endToEndId, payerReference, receivedAt, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(providerEventId, null, endToEndId, payerReference, receivedAt, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(providerEventId, txid, " ", payerReference, receivedAt, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(providerEventId, txid, endToEndId, null, receivedAt, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(providerEventId, txid, endToEndId, payerReference, null, provider));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(providerEventId, txid, endToEndId, payerReference, receivedAt, null));
    }

    private PaymentEvent createValidEvent(UUID id, UUID merchantId, BigDecimal amount) {
        return new PaymentEvent(
                id,
                merchantId,
                "evt-demo-0001",
                "DEMO20260713000000000000000001",
                "E00000000202607131730DEMO0001",
                amount,
                OffsetDateTime.parse("2026-07-13T14:30:00-03:00"),
                "cliente-demo-12",
                PaymentProvider.SIMULATED_PIX
        );
    }

    private PaymentEvent createEvent(
            String providerEventId,
            String txid,
            String endToEndId,
            String payerReference,
            OffsetDateTime receivedAt,
            PaymentProvider provider
    ) {
        return new PaymentEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                providerEventId,
                txid,
                endToEndId,
                new BigDecimal("42.00"),
                receivedAt,
                payerReference,
                provider
        );
    }
}
