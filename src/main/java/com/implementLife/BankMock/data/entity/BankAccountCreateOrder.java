package com.implementLife.BankMock.data.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BankAccountCreateOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String status;  // dismiss, accept, onReview
    private String comment; // comment for status

    @ManyToOne
    @JoinColumn(name = "template_id")
    private BankAccountTemplate template;

    public BankAccountTemplate getTemplate() {
        return template;
    }
    public void setTemplate(BankAccountTemplate template) {
        this.template = template;
    }

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
