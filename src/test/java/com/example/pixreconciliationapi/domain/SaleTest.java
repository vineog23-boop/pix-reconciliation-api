package com.example.pixreconciliationapi.domain;

import com.example.pixreconciliationapi.domain.sale.Sale;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleTest {

    @Test
    void shouldCreateSaleWhenDataIsValid() {
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
    void shouldNotCreatSaleWhenSaleValueIsZero(){

        //arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = BigDecimal.ZERO;
        

        //act + assert
        assertThrows(IllegalArgumentException.class,
        () -> new Sale(idSale,idSeller,saleValue));
        
    }

    @Test
    void shouldNotCreateSaleWhenSaleValueIsNegative(){

        //arrange
        UUID idSale = UUID.randomUUID();
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("-2.00") ;


        //act + assert
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(idSale,idSeller,saleValue));

    }

    @Test
    void shouldNotCreateSaleWhenIdSaleIsNull(){

        //arrange
        UUID idSeller = UUID.randomUUID();
        BigDecimal saleValue = new BigDecimal("100.00");

        //act + assert
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null,idSeller,saleValue));

    }

    @Test
    void shouldNotCreateSaleWhenIdSellerIsNull() {
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
    void shouldNotCreateSaleWhenSaleValueIsNull() {
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
