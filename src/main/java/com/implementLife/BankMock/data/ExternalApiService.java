package com.implementLife.BankMock.data;

import com.implementLife.BankMock.controller.dto.BillingInfo;
import com.implementLife.BankMock.controller.dto.CreateBillingRequest;

public interface ExternalApiService {
    BillingInfo createBilling(CreateBillingRequest request);
}
