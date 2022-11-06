package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BusinessApp;

import java.util.UUID;

public interface BusinessAppRepo {
    BusinessApp save(BusinessApp app);

    BusinessApp find(UUID id);
}
