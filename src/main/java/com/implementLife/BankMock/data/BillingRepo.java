package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.Billing;

import java.util.UUID;

public interface BillingRepo {

    void save(Billing billing);

    Billing findById(UUID id);
}
