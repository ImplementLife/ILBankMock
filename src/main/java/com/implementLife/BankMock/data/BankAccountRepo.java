package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BankAccount;
import com.implementLife.BankMock.entity.BankAccountCreateOrder;
import com.implementLife.BankMock.entity.Client;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepo {
    BankAccount createBankAccount();

    BankAccount findId(UUID id);

    BankAccount findByIban(String iban);

    BankAccount findByCode16x(String code16x);

    BankAccountCreateOrder getOrderById(UUID id);

    List<BankAccountCreateOrder> getAllOrdersOnReview();

    BankAccountCreateOrder createOrder(Client client);
}
