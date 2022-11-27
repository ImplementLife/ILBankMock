package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.Billing;

import java.util.UUID;

public interface PaymentService {
    boolean payByCode16x(UUID clientId, String code16xCardSander, String code16xCardReceiver, String sum);
    boolean payByIban(UUID clientId, String code16xCardSander, String ibanReceiver, String sum);

    Billing payByBillingId(UUID clientId, UUID billingId, String code16xCardSander);
}
