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
        OffsetDateTime receivedAt = OffsetDateTime.parse("2026-07-13T14:30:00-03:00");

        // Act
        PaymentEvent paymentEvent = new PaymentEvent(
                paymentEventId,
                merchantId,
                "evt-demo-0001",
                "DEMO20260713000000000000000001",
                "E00000000202607131730DEMO0001",
                new BigDecimal("42.00"),
                receivedAt,
                "cliente-demo-12",
                PaymentProvider.SIMULATED_PIX
        );

        // Assert
        assertEquals(paymentEventId, paymentEvent.getPaymentEventId());
        assertEquals(merchantId, paymentEvent.getMerchantId());
        assertEquals("evt-demo-0001", paymentEvent.getProviderEventId());
        assertEquals("DEMO20260713000000000000000001", paymentEvent.getTxid());
        assertEquals("E00000000202607131730DEMO0001", paymentEvent.getEndToEndId());
        assertEquals(new BigDecimal("42.00"), paymentEvent.getAmountReceived());
        assertEquals(receivedAt, paymentEvent.getReceivedAt());
        assertEquals("cliente-demo-12", paymentEvent.getPayerReference());
        assertEquals(PaymentProvider.SIMULATED_PIX, paymentEvent.getProvider());
    }

    @Test
    void shouldRejectMissingIds() {
        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(null, UUID.randomUUID(), new BigDecimal("42.00")));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(UUID.randomUUID(), null, new BigDecimal("42.00")));
    }

    @Test
    void shouldRejectInvalidAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(UUID.randomUUID(), UUID.randomUUID(), null));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.ZERO));

        assertThrows(IllegalArgumentException.class,
                () -> createValidEvent(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("-0.01")));
    }

    @Test
    void shouldRejectMissingProviderData() {
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(null, "txid", "endToEndId", "payer", OffsetDateTime.now(), PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent(" ", "txid", "endToEndId", "payer", OffsetDateTime.now(), PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent("event", null, "endToEndId", "payer", OffsetDateTime.now(), PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent("event", "txid", " ", "payer", OffsetDateTime.now(), PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent("event", "txid", "endToEndId", null, OffsetDateTime.now(), PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent("event", "txid", "endToEndId", "payer", null, PaymentProvider.SIMULATED_PIX));
        assertThrows(IllegalArgumentException.class,
                () -> createEvent("event", "txid", "endToEndId", "payer", OffsetDateTime.now(), null));
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
