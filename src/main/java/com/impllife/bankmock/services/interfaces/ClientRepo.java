package com.impllife.bankmock.services.interfaces;

import com.impllife.bankmock.data.entity.Client;

import java.util.UUID;

public interface ClientRepo {
    Client save(Client client);
    Client findByPhone(String phone);
    Client findById(UUID id);
}
