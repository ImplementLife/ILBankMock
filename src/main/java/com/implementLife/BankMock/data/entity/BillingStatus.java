package com.implementLife.BankMock.data.entity;

public enum BillingStatus {
    WAIT_PAY('W'),
    PAYED('P'),
    TIMEOUT_PAY('T'),
    EXPIRED('E'),
    ;

    private final char id;

    BillingStatus(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }

    public static BillingStatus getById(char id) {
        for (BillingStatus value : values()) {
            if (value.id == id) return value;
        }
        throw new IllegalArgumentException("Not exist role with id=" + id);
    }
}
