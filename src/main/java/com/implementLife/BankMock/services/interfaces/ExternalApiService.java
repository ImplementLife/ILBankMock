package com.implementLife.BankMock.services.interfaces;

import com.implementLife.BankMock.data.dto.BillingInfo;
import com.implementLife.BankMock.data.dto.CreateBillingRequest;

public interface ExternalApiService {
    BillingInfo createBilling(CreateBillingRequest request);
}
