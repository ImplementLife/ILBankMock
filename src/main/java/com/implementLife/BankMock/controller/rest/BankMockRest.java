package com.implementLife.BankMock.controller.rest;

import com.implementLife.BankMock.controller.dto.BillingInfo;
import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import com.implementLife.BankMock.data.ClientRepo;
import com.implementLife.BankMock.data.ClientService;
import com.implementLife.BankMock.data.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankMockRest implements APIController {
    @Autowired
    private ExternalApiService apiService;

    @Override
    public BillingInfo createBilling(CreateBillingRequest request) {
        return apiService.createBilling(request);
    }
}
