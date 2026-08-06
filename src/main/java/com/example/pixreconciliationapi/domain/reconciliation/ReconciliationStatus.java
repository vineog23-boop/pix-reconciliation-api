package com.example.pixreconciliationapi.domain.reconciliation;

public enum ReconciliationStatus {
    // O pagamento ainda não foi analisado.
    PENDING,

    // O pagamento corresponde ao recebível.
    MATCHED,

    // Os dados ou valores não correspondem.
    DIVERGENT,

    // O caso precisa de análise humana.
    UNDER_REVIEW,

    // Um operador resolveu o caso manualmente.
    MANUALLY_RESOLVED,

    // O pagamento foi considerado inválido para conciliação.
    REJECTED
}
