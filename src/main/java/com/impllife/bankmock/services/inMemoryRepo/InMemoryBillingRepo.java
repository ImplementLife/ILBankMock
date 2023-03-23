package com.impllife.bankmock.services.inMemoryRepo;

import com.impllife.bankmock.services.interfaces.BillingRepo;
import com.impllife.bankmock.data.entity.Billing;

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
