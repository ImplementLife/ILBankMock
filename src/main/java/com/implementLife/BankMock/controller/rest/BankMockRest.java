package com.implementLife.BankMock.controller.rest;

import com.implementLife.BankMock.data.dto.BillingInfo;
import com.implementLife.BankMock.data.dto.CreateBillingRequest;
import com.implementLife.BankMock.services.interfaces.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BankMockRest implements APIController {
    @Autowired
    private ExternalApiService apiService;

    @Override
    public BillingInfo createBilling(CreateBillingRequest request) {
        return apiService.createBilling(request);
    }

    @Override
    public BillingInfo getBillingInfo(UUID billingId) {
        throw new UnsupportedOperationException();
    }
}
