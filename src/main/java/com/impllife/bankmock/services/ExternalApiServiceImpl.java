package com.impllife.bankmock.services;

import com.impllife.bankmock.data.dto.BillingInfo;
import com.impllife.bankmock.data.dto.CreateBillingRequest;
import com.impllife.bankmock.data.entity.Billing;
import com.impllife.bankmock.data.entity.BillingStatus;
import com.impllife.bankmock.data.entity.BusinessApp;
import com.impllife.bankmock.data.repo.BillingRepo;
import com.impllife.bankmock.data.repo.BusinessAppRepo;
import com.impllife.bankmock.services.interfaces.BankAccountService;
import com.impllife.bankmock.services.interfaces.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    @Autowired
    private BusinessAppRepo appRepo;
    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private BankAccountService bankAccountService;
    @Value("${impl_life.paymentUrl}")
    private String paymentUrl;
    @Value("${impl_life.lifeTimeBillingInMinutes}")
    private int lifeTimeBillingInMinutes;

    @Override
    public BillingInfo createBilling(CreateBillingRequest request) {
        BusinessApp app = appRepo.findById(request.getAppId()).get();
        if (app == null) {
            throw new IllegalArgumentException("appId doesn't valid");
        }
        if (!app.getAccessApiToken().equals(request.getAccessToken())) {
            throw new IllegalArgumentException("accessToken doesn't valid");
        }
        if (new BigDecimal(request.getUnits()).compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("units must be positive value");
        }

        Billing billing = new Billing();

        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE, lifeTimeBillingInMinutes);
        billing.setLastDateTimeForPay(time.getTime());

        billing.setSum(request.getUnits());
        billing.setReceiverName(app.getName());
        billing.setPaymentDescription(request.getPaymentDescription());
        billing.setCompletePaymentLink(request.getCompletePaymentLink());
        billing.setBankAccountReceiver(bankAccountService.findByIban(app.getIbanReceiver()));
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
