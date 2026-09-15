package com.example.pixreconciliationapi.application;

import com.example.pixreconciliationapi.application.port.ReceivableRepository;
import com.example.pixreconciliationapi.application.usecase.RegisterReceivableUseCase;
import com.example.pixreconciliationapi.domain.receivable.Receivable;
import com.example.pixreconciliationapi.domain.receivable.ReceivableStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RegisterReceivableUseCaseTest {

    @Test
    void shouldRegisterReceivable() {
        // Arrange
        ReceivableRepository receivableRepository = mock(ReceivableRepository.class);
        RegisterReceivableUseCase useCase = new RegisterReceivableUseCase(receivableRepository);
        UUID merchantId = UUID.randomUUID();
        String txid = "PEDIDO458";
        BigDecimal expectedAmount = new BigDecimal("100.00");

        // Act
        Receivable receivable = useCase.execute(
                merchantId,
                txid,
                expectedAmount
        );

        // Assert
        assertNotNull(receivable.getReceivableId());
        assertEquals(merchantId, receivable.getMerchantId());
        assertEquals(txid, receivable.getTxid());
        assertEquals(expectedAmount, receivable.getExpectedAmount());
        assertEquals(ReceivableStatus.OPEN, receivable.getStatus());
        verify(receivableRepository).save(receivable);
    }
}
