package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.Client;

import java.util.UUID;

public interface ClientRepo {
    Client save(Client client);
    Client findByPhone(String phone);
    Client findById(UUID id);
}
