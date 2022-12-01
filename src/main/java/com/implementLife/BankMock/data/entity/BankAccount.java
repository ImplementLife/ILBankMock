package com.implementLife.BankMock.data.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String code16x;
    private String codeCvv;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    private long sumBanknote;
    private int sumPenny;
    private String name;

    private Date dateCreate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccountAction> bankAccountActions;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<BankAccountAction> getBankAccountActions() {
        return bankAccountActions;
    }
    public void setBankAccountActions(List<BankAccountAction> bankAccountActions) {
        this.bankAccountActions = bankAccountActions;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getCode16x() {
        return code16x;
    }
    public void setCode16x(String code16x) {
        this.code16x = code16x;
    }

    public String getCodeCvv() {
        return codeCvv;
    }
    public void setCodeCvv(String codeCvv) {
        this.codeCvv = codeCvv;
    }

    public long getSumBanknote() {
        return sumBanknote;
    }
    public void setSumBanknote(long sumBanknote) {
        this.sumBanknote = sumBanknote;
    }

    public int getSumPenny() {
        return sumPenny;
    }
    public void setSumPenny(int sumPenny) {
        this.sumPenny = sumPenny;
    }
}
