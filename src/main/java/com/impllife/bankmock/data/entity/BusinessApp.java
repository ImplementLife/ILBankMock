package com.impllife.bankmock.data.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BusinessApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID accessApiToken;
    private String ibanReceiver;
    private String name;
    private String urlSendResult;
    private boolean needSendResult;
    @ManyToOne
    @JoinColumn(name = "client_id")
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

    public String getUrlSendResult() {
        return urlSendResult;
    }
    public void setUrlSendResult(String urlSendResult) {
        this.urlSendResult = urlSendResult;
    }

    public boolean isNeedSendResult() {
        return needSendResult;
    }
    public void setNeedSendResult(boolean needSendResult) {
        this.needSendResult = needSendResult;
    }

    public String getIbanReceiver() {
        return ibanReceiver;
    }
    public void setIbanReceiver(String ibanReceiver) {
        this.ibanReceiver = ibanReceiver;
    }
}
