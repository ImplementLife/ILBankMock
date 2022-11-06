package com.implementLife.BankMock.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BankAccount {
    private UUID id;
    private String code16x;
    private String codeCvv;
    private String iban;

    private Currency currency;
    private long sumBanknote;
    private int sumPenny;
    private String name;

    private Date dateCreate;

    private List<BankAccountAction> bankAccountActions;

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
