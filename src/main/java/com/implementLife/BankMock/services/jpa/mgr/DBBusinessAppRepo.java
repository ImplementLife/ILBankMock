package com.implementLife.BankMock.services.jpa.mgr;

import com.implementLife.BankMock.data.entity.BusinessApp;
import com.implementLife.BankMock.services.interfaces.BusinessAppRepo;
import com.implementLife.BankMock.services.jpa.repo.BusinessAppRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class DBBusinessAppRepo implements BusinessAppRepo {
    @Autowired
    private BusinessAppRepository businessAppRepository;
    @Override
    public BusinessApp save(BusinessApp app) {
        return businessAppRepository.saveAndFlush(app);
    }

    @Override
    public BusinessApp findById(UUID id) {
        return businessAppRepository.findById(id).orElseThrow();
    }
}
