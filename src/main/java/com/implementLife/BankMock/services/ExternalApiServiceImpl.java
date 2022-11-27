package com.implementLife.BankMock.services;

import com.implementLife.BankMock.data.dto.BillingInfo;
import com.implementLife.BankMock.data.dto.CreateBillingRequest;
import com.implementLife.BankMock.services.interfaces.BankAccountRepo;
import com.implementLife.BankMock.services.interfaces.BillingRepo;
import com.implementLife.BankMock.services.interfaces.BusinessAppRepo;
import com.implementLife.BankMock.services.interfaces.ExternalApiService;
import com.implementLife.BankMock.data.entity.Billing;
import com.implementLife.BankMock.data.entity.BillingStatus;
import com.implementLife.BankMock.data.entity.BusinessApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;

public class ExternalApiServiceImpl implements ExternalApiService {
    @Autowired
    private BusinessAppRepo appRepo;
    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private BankAccountRepo bankAccountRepo;
    @Value("${impl_life.paymentUrl}")
    private String paymentUrl;
    @Value("${impl_life.lifeTimeBillingInMinutes}")
    private int lifeTimeBillingInMinutes;

    @Override
    public BillingInfo createBilling(CreateBillingRequest request) {
        BusinessApp app = appRepo.find(request.getAppId());
        if (app == null) {
            throw new IllegalArgumentException("appId doesn't valid");
        }
        if (!app.getAccessApiToken().equals(request.getAccessToken())) {
            throw new IllegalArgumentException("accessToken doesn't valid");
        }

        Billing billing = new Billing();

        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE, lifeTimeBillingInMinutes);
        billing.setLastDateTimeForPay(time.getTime());

        billing.setSum(request.getUnits());
        billing.setReceiverName(app.getName());
        billing.setPaymentDescription(request.getPaymentDescription());
        billing.setCompletePaymentLink(request.getCompletePaymentLink());
        billing.setBankAccountReceiver(bankAccountRepo.findByIban(app.getIbanReceiver()));
        billing.setStatus(BillingStatus.WAIT_PAY);
        billingRepo.save(billing);

        BillingInfo info = new BillingInfo();
        info.setPaymentPageUrl(paymentUrl + "?billingId=" + billing.getId());
        info.setLastDateTimeForPay(billing.getLastDateTimeForPay().getTime());
        return info;
    }
}
