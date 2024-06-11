package com.impllife.bankmock.services.interfaces;

import com.impllife.bankmock.data.entity.BankAccount;
import com.impllife.bankmock.data.entity.BankAccountCreateOrder;
import com.impllife.bankmock.data.entity.BankAccountTemplate;
import com.impllife.bankmock.data.entity.Client;

import java.util.List;
import java.util.UUID;

public interface BankAccountService {
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
