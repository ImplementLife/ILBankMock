package com.impllife.bankmock.services.interfaces;

import com.impllife.bankmock.data.entity.BusinessApp;

import java.util.UUID;

public interface BusinessAppRepo {
    BusinessApp save(BusinessApp app);

    BusinessApp findById(UUID id);
}
