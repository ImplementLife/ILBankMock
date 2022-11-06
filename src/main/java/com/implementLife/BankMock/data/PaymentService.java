package com.implementLife.BankMock.data;

import java.util.UUID;

public interface PaymentService {
    boolean payByCode16x(UUID clientId, String code16xCardSander, String code16xCardReceiver, String sum);
    boolean payByIban(UUID clientId, String code16xCardSander, String ibanReceiver, String sum);

    boolean payByBillingId(UUID billingId, String code16xCardSander);
}
