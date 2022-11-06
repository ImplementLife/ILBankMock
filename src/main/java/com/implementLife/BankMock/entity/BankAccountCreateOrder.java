package com.implementLife.BankMock.entity;

import java.util.UUID;

public class BankAccountCreateOrder {
    private UUID id;

    private Client client;
    private String status;  // dismiss, accept, onReview
    private String comment; // comment for status

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
