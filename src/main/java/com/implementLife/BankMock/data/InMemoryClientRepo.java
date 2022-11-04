package com.implementLife.BankMock.data;

import com.implementLife.BankMock.config.security.Role;
import com.implementLife.BankMock.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryClientRepo implements ClientRepo {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryClientRepo.class);
    private final Map<String, Client> accounts = new HashMap<>();

    @PostConstruct
    private void initAccounts() {
        LOG.info("Creating temp users:");
        create("123", "123");
        create("321", "321");
    }
    private void create(String phone, String pass) {
        Client client = new Client();
        client.setPass(pass);
        client.setPhoneNumber(phone);

        save(client);

        LOG.info("\t" + client.getPhoneNumber() + " : " + client.getPass());
    }

    @Override
    public Client save(Client client) {
        client.setRoles(String.valueOf((Role.USER.getId())));
        client.setId(UUID.randomUUID());
        accounts.put(client.getPhoneNumber(), client);
        return client;
    }

    @Override
    public Client getByPhone(String phone) {
        return accounts.get(phone);
    }
}
