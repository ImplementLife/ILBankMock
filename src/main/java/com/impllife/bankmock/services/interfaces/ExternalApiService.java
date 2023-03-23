package com.impllife.bankmock.services.interfaces;

import com.impllife.bankmock.data.dto.BillingInfo;
import com.impllife.bankmock.data.dto.CreateBillingRequest;

public interface ExternalApiService {
    BillingInfo createBilling(CreateBillingRequest request);
}
