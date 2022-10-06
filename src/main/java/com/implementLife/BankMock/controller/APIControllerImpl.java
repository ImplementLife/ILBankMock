package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIControllerImpl implements APIController {

    @Override
    public void createClientAccount() {

    }

    @Override
    public void createBankAccount(String clientId, String CurrencyId) {

    }

    @Override
    public void updatePersonalInfo() {

    }

    @Override
    public void createBilling(CreateBillingRequest request) {

    }

    @Override
    public void payBilling(PayBillingRequest request) {

    }

}
