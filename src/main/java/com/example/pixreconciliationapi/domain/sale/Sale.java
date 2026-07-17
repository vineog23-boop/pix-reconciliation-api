package com.example.pixreconciliationapi.domain.sale;

import java.math.BigDecimal;
import java.util.UUID;

public class Sale {

    private final UUID idSale;
    private final UUID idSeller;
    private final BigDecimal saleValue;

    public Sale(UUID idSale, UUID idSeller, BigDecimal saleValue) {
        if (idSale == null) {
            throw new IllegalArgumentException(
                    "O id da venda não pode ser nulo"
            );
        }

        if (idSeller == null) {
            throw new IllegalArgumentException(
                    "O id do vendedor não pode ser nulo"
            );
        }

        if (saleValue == null) {
            throw new IllegalArgumentException(
                    "O valor da venda não pode ser nulo"
            );
        }

        if (saleValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor da venda deve ser maior que zero"
            );
        }

        this.idSale = idSale;
        this.idSeller = idSeller;
        this.saleValue = saleValue;
    }

    public UUID getIdSale() {
        return idSale;
    }

    public UUID getIdSeller() {
        return idSeller;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }
}
