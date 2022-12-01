package com.implementLife.BankMock.services.inMemoryRepo;

import com.implementLife.BankMock.services.interfaces.BillingRepo;
import com.implementLife.BankMock.data.entity.Billing;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class InMemoryBillingRepo implements BillingRepo {
    private final Map<UUID, Billing> billingsById = new TreeMap<>();
    @Override
    public void save(Billing billing) {
        billing.setId(UUID.randomUUID());
        billingsById.put(billing.getId(), billing);
    }

    @Override
    public Billing findById(UUID id) {
        return billingsById.get(id);
    }
}
