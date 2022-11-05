package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BankAccountAction;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    boolean pay(UUID clientId, String code16xCurrentClient, String code16xOtherClient, String sum);

    List<BankAccountAction> getHistory(UUID clientId, UUID bankAccountId);
}
