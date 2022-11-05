package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BankAccount;

import java.util.UUID;

public interface BankAccountRepo {
    BankAccount createBankAccount();
    BankAccount find(UUID id);
    BankAccount find(String code16x);
}
