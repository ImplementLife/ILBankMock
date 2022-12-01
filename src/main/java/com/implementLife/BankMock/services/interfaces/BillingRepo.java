package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.Billing;

import java.util.UUID;

public interface BillingRepo {

    void save(Billing billing);

    Billing findById(UUID id);
}
