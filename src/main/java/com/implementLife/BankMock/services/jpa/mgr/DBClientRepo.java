package com.implementLife.BankMock.services.jpa.mgr;

import com.implementLife.BankMock.data.entity.Client;
import com.implementLife.BankMock.data.entity.security.Role;
import com.implementLife.BankMock.services.interfaces.ClientRepo;
import com.implementLife.BankMock.services.jpa.repo.ClientRepository;
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
