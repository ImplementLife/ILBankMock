package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.Client;

import java.util.UUID;

public interface ClientRepo {
    Client save(Client client);
    Client getByPhone(String phone);
    Client getById(UUID id);
}
