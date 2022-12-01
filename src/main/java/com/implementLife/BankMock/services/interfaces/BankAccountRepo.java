package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.BankAccount;
import com.implementLife.BankMock.data.entity.BankAccountCreateOrder;
import com.implementLife.BankMock.data.entity.BankAccountTemplate;
import com.implementLife.BankMock.data.entity.Client;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepo {
    BankAccount createBankAccount(BankAccountTemplate template);

    BankAccount findById(UUID id);

    BankAccount findByIban(String iban);

    BankAccount findByCode16x(String code16x);

    BankAccountCreateOrder findOrderById(UUID id);

    List<BankAccountCreateOrder> findAllOrdersOnReview();

    BankAccountCreateOrder createOrder(Client client);

    void saveOrder(BankAccountCreateOrder order);

    boolean save(BankAccount bankAccount);
}
