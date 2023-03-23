package com.impllife.bankmock.services;

import com.impllife.bankmock.data.dto.BillingInfo;
import com.impllife.bankmock.data.dto.CreateBillingRequest;
import com.impllife.bankmock.services.interfaces.BankAccountRepo;
import com.impllife.bankmock.services.interfaces.BillingRepo;
import com.impllife.bankmock.services.interfaces.BusinessAppRepo;
import com.impllife.bankmock.services.interfaces.ExternalApiService;
import com.impllife.bankmock.data.entity.Billing;
import com.impllife.bankmock.data.entity.BillingStatus;
import com.impllife.bankmock.data.entity.BusinessApp;
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
        BusinessApp app = appRepo.findById(request.getAppId());
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
        billing.setBusinessApp(app);
        billingRepo.save(billing);

        BillingInfo info = new BillingInfo();
        info.setPaymentPageUrl(paymentUrl + "?billingId=" + billing.getId());
        info.setLastDateTimeForPay(billing.getLastDateTimeForPay().getTime());
        info.setStatus(billing.getStatus().toString());
        return info;
    }
}
