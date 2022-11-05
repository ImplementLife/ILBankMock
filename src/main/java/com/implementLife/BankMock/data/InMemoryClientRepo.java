package com.implementLife.BankMock.data;

import com.implementLife.BankMock.config.security.Role;
import com.implementLife.BankMock.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryClientRepo implements ClientRepo {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryClientRepo.class);
    private final Map<String, Client> byPhone = new HashMap<>();
    private final Map<UUID, Client> byId = new HashMap<>();
    @Autowired
    private BankAccountRepo bankAccountRepo;

    @PostConstruct
    private void initAccounts() {
        LOG.info("Creating temp users:");
        create("manager", "manager", Role.MANAGER.getId());
        create("user", "user", Role.USER.getId());
        create("user2", "user", Role.USER.getId());
        create("admin", "admin", Role.ADMIN.getId());
    }
    private void create(String phone, String pass, char id) {
        Client client = new Client();
        client.setPass(pass);
        client.setPhoneNumber(phone);
        client.setRoles("" + id);
        client.setId(UUID.randomUUID());
        client.setBankAccounts(Collections.singletonList(bankAccountRepo.createBankAccount()));

        finalSave(client);

        LOG.info("\t" + client.getPhoneNumber() + " : " + client.getPass());
    }
    private void finalSave(Client client) {
        byId.put(client.getId(), client);
        byPhone.put(client.getPhoneNumber(), client);
    }

    @Override
    public Client save(Client client) {
        client.setRoles(String.valueOf((Role.USER.getId())));
        client.setId(UUID.randomUUID());
        finalSave(client);
        return client;
    }

    @Override
    public Client getByPhone(String phone) {
        return byPhone.get(phone);
    }

    @Override
    public Client getById(UUID id) {
        return byId.get(id);
    }
}
