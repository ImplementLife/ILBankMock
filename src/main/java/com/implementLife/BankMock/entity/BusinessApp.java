package com.implementLife.BankMock.entity;

import java.util.UUID;

public class BusinessApp {
    private UUID id;
    private UUID accessApiToken;
    private String name;
    private Client client;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public UUID getAccessApiToken() {
        return accessApiToken;
    }
    public void setAccessApiToken(UUID accessApiToken) {
        this.accessApiToken = accessApiToken;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
