package com.implementLife.BankMock.controller.rest;

import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import com.implementLife.BankMock.data.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankMockRest implements APIController {
    @Autowired
    private ClientRepo clientRepo;

    @Override
    public void createBilling(CreateBillingRequest request) {

    }

    @Override
    public void payBilling(PayBillingRequest request) {

    }

}
