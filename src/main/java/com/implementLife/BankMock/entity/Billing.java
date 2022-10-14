package com.implementLife.BankMock.entity;

import java.util.Date;
import java.util.UUID;

public class Billing {
    private UUID id;

    private BankAccount bankAccountReceiver;

    private Date lastDateTimeForPay;

    private char status;

    private String sum;
}
