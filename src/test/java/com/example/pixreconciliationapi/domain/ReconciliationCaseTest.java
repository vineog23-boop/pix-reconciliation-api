package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.reconciliation.ReconciliationCase;
import com.example.pixreconciliationapi.domain.reconciliation.ReconciliationStatus;
import org.junit.jupiter.api.Test;

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
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectNullMerchantId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        UUID.randomUUID(),
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectNullReceivableId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectNullEventId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReconciliationCase(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null
                )
        );
    }
}
