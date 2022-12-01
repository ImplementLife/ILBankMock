package com.implementLife.BankMock.data.entity;

import java.util.UUID;

public class BusinessApp {
    private UUID id;
    private UUID accessApiToken;
    private String ibanReceiver;
    private String name;
    private String urlCompleteRedirect;
    private String urlSendResult;
    private boolean needSendResult;
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

    public String getUrlCompleteRedirect() {
        return urlCompleteRedirect;
    }
    public void setUrlCompleteRedirect(String urlCompleteRedirect) {
        this.urlCompleteRedirect = urlCompleteRedirect;
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
