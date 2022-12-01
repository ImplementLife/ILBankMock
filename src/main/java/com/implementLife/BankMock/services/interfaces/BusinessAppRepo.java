package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.BusinessApp;

import java.util.UUID;

public interface BusinessAppRepo {
    BusinessApp save(BusinessApp app);

    BusinessApp findById(UUID id);
}
