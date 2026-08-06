package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.sale.Sale;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleTest {

    @Test
    void shouldCreateValidSale() {
        // Arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("100.00");

        // Act
        Sale sale = new Sale(idSale, idSeller, saleValue);

        // Assert
        assertEquals(idSale, sale.getIdSale());
        assertEquals(idSeller, sale.getIdSeller());
        assertEquals(saleValue, sale.getSaleValue());
    }

    @Test
    void shouldRejectZeroValue() {
        // Arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = BigDecimal.ZERO;

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sale(idSale, idSeller, saleValue)
        );
    }

    @Test
    void shouldRejectNegativeValue() {
        // Arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("-2.00");

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sale(idSale, idSeller, saleValue)
        );
    }

    @Test
    void shouldRejectNullSaleId() {
        // Arrange
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("100.00");

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sale(null, idSeller, saleValue)
        );
    }

    @Test
    void shouldRejectNullSellerId() {
        // Arrange
        UUID idSale = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("100.00");

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sale(idSale, null, saleValue)
        );
    }

    @Test
    void shouldRejectNullValue() {
        // Arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sale(idSale, idSeller, null)
        );
    }
}
