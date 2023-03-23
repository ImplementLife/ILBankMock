package com.impllife.bankmock.data.entity;

public enum BillingStatus {
    WAIT_PAY('W'),
    PAYED('P'),
    TIMEOUT_PAY('T'),
    EXPIRED('E'),
    CANCELED('C'),
    ;

    private final char id;

    BillingStatus(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }

}
