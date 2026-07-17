package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.receivable.Receivable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceivableTest {

    @Nested
    class WhenValuesAreValid {

        @Test
        void shouldCreateReceivableWhenValuesAreValid() {

            // Arrange

            UUID idReceivable = UUID.randomUUID();
            UUID idSale = UUID.randomUUID();
            UUID txId = UUID.randomUUID();
            BigDecimal expectedValue = new BigDecimal("100.00");

            // Act
            Receivable receivable = new Receivable(idReceivable, idSale, txId, expectedValue);

            // Assert

            assertEquals(idReceivable, receivable.getIdReceivable());
            assertEquals(idSale, receivable.getIdSale());
            assertEquals(txId, receivable.getTxId());
            assertEquals(expectedValue, receivable.getExpectedValue());
        }
    }

    @Nested
    class WhenValuesAreInvalid {

        @Test
        void shouldNotCreateReceivableWhenIdReceivableIsNull() {
            // Arrange
            UUID idSale = UUID.randomUUID();
            UUID txId = UUID.randomUUID();
            BigDecimal expectedValue = new BigDecimal("100.00");

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(null, idSale, txId, expectedValue)
            );
        }

        @Test
        void shouldNotCreateReceivableWhenIdSaleIsNull() {
            // Arrange
            UUID idReceivable = UUID.randomUUID();
            UUID txId = UUID.randomUUID();
            BigDecimal expectedValue = new BigDecimal("100.00");

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(idReceivable, null, txId, expectedValue)
            );
        }

        @Test
        void shouldNotCreateReceivableWhenTxIdIsNull() {
            // Arrange
            UUID idReceivable = UUID.randomUUID();
            UUID idSale = UUID.randomUUID();
            BigDecimal expectedValue = new BigDecimal("100.00");

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(idReceivable, idSale, null, expectedValue)
            );
        }

        @Test
        void shouldNotCreateReceivableWhenExpectedValueIsNull() {
            // Arrange
            UUID idReceivable = UUID.randomUUID();
            UUID idSale = UUID.randomUUID();
            UUID txId = UUID.randomUUID();

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(idReceivable, idSale, txId, null)
            );
        }

        @Test
        void shouldNotCreateReceivableWhenExpectedValueIsZero() {
            // Arrange
            UUID idReceivable = UUID.randomUUID();
            UUID idSale = UUID.randomUUID();
            UUID txId = UUID.randomUUID();
            BigDecimal expectedValue = BigDecimal.ZERO;

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(idReceivable, idSale, txId, expectedValue)
            );
        }

        @Test
        void shouldNotCreateReceivableWhenExpectedValueIsNegative() {
            // Arrange
            UUID idReceivable = UUID.randomUUID();
            UUID idSale = UUID.randomUUID();
            UUID txId = UUID.randomUUID();
            BigDecimal expectedValue = new BigDecimal("-2.00");

            // Act + Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Receivable(idReceivable, idSale, txId, expectedValue)
            );
        }
    }
}
