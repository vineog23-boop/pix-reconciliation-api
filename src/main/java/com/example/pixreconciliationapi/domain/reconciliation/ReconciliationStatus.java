package com.example.pixreconciliationapi.domain.reconciliation;

public enum ReconciliationStatus {
    // O pagamento ainda não foi analisado.
    PENDING,

    // O evento de pagamento foi associado ao recebível correto.
    MATCHED,

    // Existem dados conflitantes que impedem uma associação segura.
    DIVERGENT,

    // O caso precisa de análise humana.
    UNDER_REVIEW,

    // Um operador resolveu o caso manualmente.
    MANUALLY_RESOLVED,

    // O evento de pagamento não pertence ao recebível analisado.
    REJECTED
}
