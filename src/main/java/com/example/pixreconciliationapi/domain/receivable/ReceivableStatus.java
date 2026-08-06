package com.example.pixreconciliationapi.domain.receivable;

public enum ReceivableStatus {
    // Ainda não recebeu nenhum pagamento.
    OPEN,

    // Recebeu apenas parte do valor esperado.
    PARTIALLY_PAID,

    // Recebeu exatamente o valor esperado.
    PAID,

    // Passou do vencimento sem pagamento suficiente.
    OVERDUE,

    // Foi cancelado e não deve receber novos pagamentos.
    CANCELLED
}
