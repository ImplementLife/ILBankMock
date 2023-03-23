package com.impllife.bankmock.services.jpa.mgr;

import com.impllife.bankmock.data.entity.Client;
import com.impllife.bankmock.services.interfaces.ClientRepo;
import com.impllife.bankmock.services.jpa.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class DBClientRepo implements ClientRepo {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Client findByPhone(String phone) {
        return clientRepository.findByPhoneNumber(phone).orElseThrow();
    }

    @Override
    public Client findById(UUID id) {
        return clientRepository.findById(id).orElseThrow();
    }
}
