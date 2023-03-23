package com.impllife.bankmock.services.interfaces;

import com.impllife.bankmock.data.entity.Billing;

import java.util.UUID;

public interface BillingRepo {

    void save(Billing billing);

    Billing findById(UUID id);
}
