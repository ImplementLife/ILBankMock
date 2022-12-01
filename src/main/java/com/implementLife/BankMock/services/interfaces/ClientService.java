package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.entity.BankAccountAction;
import com.implementLife.BankMock.data.entity.BusinessApp;
import com.implementLife.BankMock.data.entity.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<BankAccountAction> getHistory(UUID clientId, UUID bankAccountId);

    boolean createOrder(Client client);

    boolean processingCreateOrder(UUID idOrder, String action);

    void addBusinessRole(Client client);

    void registerBusinessApp(Client client, BusinessApp name);

    void requestBusinessBankAccount(Client client);
}
