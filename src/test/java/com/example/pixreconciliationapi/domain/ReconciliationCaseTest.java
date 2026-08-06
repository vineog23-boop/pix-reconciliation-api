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
}
