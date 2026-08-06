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
        String txid = "PEDIDO458";
        BigDecimal expectedAmount = new BigDecimal("100.00");

        // Act
        Receivable receivable = new Receivable(
                receivableId,
                merchantId,
                txid,
                expectedAmount
        );

        // Assert
        assertEquals(receivableId, receivable.getReceivableId());
        assertEquals(merchantId, receivable.getMerchantId());
        assertEquals(txid, receivable.getTxid());
        assertEquals(expectedAmount, receivable.getExpectedAmount());
        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldRejectInvalidData() {
        // Arrange
        UUID receivableId = UUID.randomUUID();
        UUID merchantId = UUID.randomUUID();
        String txid = "PEDIDO458";
        String blankTxid = " ";
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = new BigDecimal("-0.01");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(null, merchantId, txid, expectedAmount));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(receivableId, null, txid, expectedAmount));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(receivableId, merchantId, blankTxid, expectedAmount));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(receivableId, merchantId, txid, null));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(receivableId, merchantId, txid, zeroAmount));
        assertThrows(IllegalArgumentException.class,
                () -> new Receivable(receivableId, merchantId, txid, negativeAmount));
    }

    @Test
    void shouldRegisterPartialPayment() {
        // Arrange
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal paymentAmount = new BigDecimal("40.00");
        Receivable receivable = createValidReceivable(expectedAmount);

        // Act
        receivable.registerPayment(paymentAmount);

        // Assert
        assertEquals(paymentAmount, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.PARTIALLY_PAID, receivable.getStatus());
    }

    @Test
    void shouldCompletePayment() {
        // Arrange
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal firstPayment = new BigDecimal("40.00");
        BigDecimal secondPayment = new BigDecimal("60.00");
        Receivable receivable = createValidReceivable(expectedAmount);

        // Act
        receivable.registerPayment(firstPayment);
        receivable.registerPayment(secondPayment);

        // Assert
        assertEquals(expectedAmount, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.PAID, receivable.getStatus());
    }

    @Test
    void shouldRejectExcessAmount() {
        // Arrange
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal excessAmount = new BigDecimal("100.01");
        Receivable receivable = createValidReceivable(expectedAmount);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> receivable.registerPayment(excessAmount)
        );

        assertEquals(BigDecimal.ZERO, receivable.getReceivedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
    }

    @Test
    void shouldRejectInvalidPayment() {
        // Arrange
        BigDecimal expectedAmount = new BigDecimal("100.00");
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = new BigDecimal("-0.01");
        Receivable receivable = createValidReceivable(expectedAmount);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(null));
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(zeroAmount));
        assertThrows(IllegalArgumentException.class,
                () -> receivable.registerPayment(negativeAmount));
    }

    private Receivable createValidReceivable(BigDecimal expectedAmount) {
        return new Receivable(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "PEDIDO458",
                expectedAmount
        );
    }
}
