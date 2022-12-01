package com.implementLife.BankMock.services.jpa.mgr;

import com.implementLife.BankMock.data.entity.Billing;
import com.implementLife.BankMock.services.interfaces.BillingRepo;
import com.implementLife.BankMock.services.jpa.repo.BillingRepository;
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
