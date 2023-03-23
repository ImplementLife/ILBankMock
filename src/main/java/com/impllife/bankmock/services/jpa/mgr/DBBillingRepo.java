package com.impllife.bankmock.services.jpa.mgr;

import com.impllife.bankmock.data.entity.Billing;
import com.impllife.bankmock.services.interfaces.BillingRepo;
import com.impllife.bankmock.services.jpa.repo.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class DBBillingRepo implements BillingRepo {
    @Autowired
    private BillingRepository billingRepository;
    @Override
    public void save(Billing billing) {
        billingRepository.saveAndFlush(billing);
    }

    @Override
    public Billing findById(UUID id) {
        return billingRepository.findById(id).orElseThrow();
    }
}
