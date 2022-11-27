package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.BankAccount;
import com.implementLife.BankMock.data.entity.BankAccountCreateOrder;
import com.implementLife.BankMock.data.entity.Client;

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
