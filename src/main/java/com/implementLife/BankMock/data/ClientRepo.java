package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.Client;

public interface ClientRepo {
    Client save(Client client);
    Client getByPhone(String phone);
}
