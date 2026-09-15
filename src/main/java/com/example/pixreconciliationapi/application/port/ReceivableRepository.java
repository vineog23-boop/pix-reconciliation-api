package com.example.pixreconciliationapi.application.port;

import com.example.pixreconciliationapi.domain.receivable.Receivable;

public interface ReceivableRepository {

    void save(Receivable receivable);
}
