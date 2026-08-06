package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.payment.PaymentEvent;
import com.example.pixreconciliationapi.domain.payment.PaymentProvider;
import com.example.pixreconciliationapi.domain.receivable.Receivable;
import com.example.pixreconciliationapi.domain.receivable.ReceivableStatus;
import com.example.pixreconciliationapi.domain.reconciliation.ReconciliationCase;
import com.example.pixreconciliationapi.domain.reconciliation.ReconciliationStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReconciliationCaseTest {

    @Test
    void shouldCreatePendingCase() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();

        // Act
        ReconciliationCase reconciliationCase = new ReconciliationCase(
                reconciliationCaseId,
                merchantId,
                receivableId,
                paymentEventId
        );

        // Assert
        assertEquals(reconciliationCaseId, reconciliationCase.getReconciliationCaseId());
        assertEquals(merchantId, reconciliationCase.getMerchantId());
        assertEquals(receivableId, reconciliationCase.getReceivableId());
        assertEquals(paymentEventId, reconciliationCase.getPaymentEventId());
        assertEquals(ReconciliationStatus.PENDING, reconciliationCase.getStatus());
    }

    @Test
    void shouldRejectNullCaseId() {
        // Arrange
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        null,
                        merchantId,
                        receivableId,
                        paymentEventId
                )
        );
    }

    @Test
    void shouldRejectNullMerchantId() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        reconciliationCaseId,
                        null,
                        receivableId,
                        paymentEventId
                )
        );
    }

    @Test
    void shouldRejectNullReceivableId() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        reconciliationCaseId,
                        merchantId,
                        null,
                        paymentEventId
                )
        );
    }

    @Test
    void shouldRejectNullEventId() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        reconciliationCaseId,
                        merchantId,
                        receivableId,
                        null
                )
        );
    }

    @Test
    void shouldMatchPayment() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();
        String txid = "PEDIDO458";
        BigDecimal amount = new BigDecimal("100.00");

        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                txid,
                amount
        );

        PaymentEvent paymentEvent = createPaymentEvent(
                paymentEventId,
                merchantId,
                txid,
                amount
        );

        ReconciliationCase reconciliationCase = new ReconciliationCase(
                reconciliationCaseId,
                merchantId,
                receivableId,
                paymentEventId
        );

        // Act
        reconciliationCase.reconcile(receivable, paymentEvent);

        // Assert
        assertEquals(ReconciliationStatus.MATCHED, reconciliationCase.getStatus());
        assertEquals(amount, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.PAID, receivable.getStatus());
    }

    @Test
    void shouldMarkDifferentTxidAsDivergent() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();
        String receivableTxid = "PEDIDO458";
        String paymentTxid = "PEDIDO999";
        BigDecimal amount = new BigDecimal("100.00");

        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                receivableTxid,
                amount
        );

        PaymentEvent paymentEvent = createPaymentEvent(
                paymentEventId,
                merchantId,
                paymentTxid,
                amount
        );

        ReconciliationCase reconciliationCase = new ReconciliationCase(
                reconciliationCaseId,
                merchantId,
                receivableId,
                paymentEventId
        );

        // Act
        reconciliationCase.reconcile(receivable, paymentEvent);

        // Assert
        assertEquals(ReconciliationStatus.DIVERGENT, reconciliationCase.getStatus());
        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldMarkDifferentAmountAsDivergent() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();
        String txid = "PEDIDO458";
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal receivedAmount = new BigDecimal("80.00");

        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                txid,
                expectedAmount
        );

        PaymentEvent paymentEvent = createPaymentEvent(
                paymentEventId,
                merchantId,
                txid,
                receivedAmount
        );

        ReconciliationCase reconciliationCase = new ReconciliationCase(
                reconciliationCaseId,
                merchantId,
                receivableId,
                paymentEventId
        );

        // Act
        reconciliationCase.reconcile(receivable, paymentEvent);

        // Assert
        assertEquals(ReconciliationStatus.DIVERGENT, reconciliationCase.getStatus());
        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldRejectDifferentMerchant() {
        // Arrange
        UUID reconciliationCaseId = UUID.randomUUID();
        UUID caseMerchantId = UUID.randomUUID();
        UUID paymentMerchantId = UUID.randomUUID();
        UUID receivableId = UUID.randomUUID();
        UUID paymentEventId = UUID.randomUUID();
        String txid = "PEDIDO458";
        BigDecimal amount = new BigDecimal("100.00");

        Receivable receivable = new Receivable(
                receivableId,
                caseMerchantId,
                txid,
                amount
        );

        PaymentEvent paymentEvent = createPaymentEvent(
                paymentEventId,
                paymentMerchantId,
                txid,
                amount
        );

        ReconciliationCase reconciliationCase = new ReconciliationCase(
                reconciliationCaseId,
                caseMerchantId,
                receivableId,
                paymentEventId
        );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reconciliationCase.reconcile(receivable, paymentEvent)
        );

        assertEquals(ReconciliationStatus.PENDING, reconciliationCase.getStatus());
        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
    }

    private PaymentEvent createPaymentEvent(
            UUID paymentEventId,
            UUID merchantId,
            String txid,
            BigDecimal amount
    ) {
        String providerEventId = "EVENTO123";
        String endToEndId = "E123456789";
        OffsetDateTime receivedAt = OffsetDateTime.parse("2026-08-06T14:00:00-03:00");
        String payerReference = "CLIENTE123";
        PaymentProvider provider = PaymentProvider.SIMULATED_PIX;

        return new PaymentEvent(
                paymentEventId,
                merchantId,
                providerEventId,
                txid,
                endToEndId,
                amount,
                receivedAt,
                payerReference,
                provider
        );
    }
}
