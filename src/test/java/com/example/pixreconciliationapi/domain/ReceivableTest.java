package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.receivable.Receivable;
import com.example.pixreconciliationapi.domain.receivable.ReceivableStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceivableTest {

    @Test
    void shouldCreateOpenReceivable() {
        // Arrange
        UUID receivableId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();

        // Act
        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                "PEDIDO458",
                new BigDecimal("100.00")
        );

        // Assert
        assertEquals(receivableId, receivable.getReceivableId());
        assertEquals(merchantId, receivable.getMerchantId());
        assertEquals("PEDIDO458", receivable.getTxid());
        assertEquals(new BigDecimal("100.00"), receivable.getExpectedAmount());
        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldRejectInvalidData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(null, UUID.randomUUID(), "PEDIDO458", new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(UUID.randomUUID(), null, "PEDIDO458", new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(UUID.randomUUID(), UUID.randomUUID(), " ", new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(UUID.randomUUID(), UUID.randomUUID(), "PEDIDO458", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(UUID.randomUUID(), UUID.randomUUID(), "PEDIDO458", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(UUID.randomUUID(), UUID.randomUUID(), "PEDIDO458", new BigDecimal("-0.01")));
    }

    @Test
    void shouldRegisterPartialPayment() {
        // Arrange
        Receivable receivable = createValidReceivable("100.00");

        // Act
        receivable.registerPayment(new BigDecimal("40.00"));

        // Assert
        assertEquals(new BigDecimal("40.00"), receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.PARTIALLY_PAID, receivable.getStatus());
    }

    @Test
    void shouldCompletePayment() {
        // Arrange
        Receivable receivable = createValidReceivable("100.00");

        // Act
        receivable.registerPayment(new BigDecimal("40.00"));
        receivable.registerPayment(new BigDecimal("60.00"));

        // Assert
        assertEquals(new BigDecimal("100.00"), receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.PAID, receivable.getStatus());
    }

    @Test
    void shouldRejectExcessAmount() {
        // Arrange
        Receivable receivable = createValidReceivable("100.00");

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> receivable.registerPayment(new BigDecimal("100.01"))
        );

        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldRejectInvalidPayment() {
        // Arrange
        Receivable receivable = createValidReceivable("100.00");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(null));
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(new BigDecimal("-0.01")));
    }

    private Receivable createValidReceivable(String expectedAmount) {
        return new Receivable(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "PEDIDO458",
                new BigDecimal(expectedAmount)
        );
    }
}
