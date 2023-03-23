package com.impllife.bankmock.services.jpa.mgr;

import com.impllife.bankmock.data.entity.BusinessApp;
import com.impllife.bankmock.services.interfaces.BusinessAppRepo;
import com.impllife.bankmock.services.jpa.repo.BusinessAppRepository;
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
