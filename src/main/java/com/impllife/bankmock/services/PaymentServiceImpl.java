package com.impllife.bankmock.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impllife.bankmock.data.dto.NotifyMessage;
import com.impllife.bankmock.data.entity.*;
import com.impllife.bankmock.services.interfaces.BankAccountRepo;
import com.impllife.bankmock.services.interfaces.BillingRepo;
import com.impllife.bankmock.services.interfaces.ClientRepo;
import com.impllife.bankmock.services.interfaces.PaymentService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private BankAccountRepo bankAccountRepo;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Executor executor;
    private final RestTemplate template = new RestTemplate();

    private BankAccountAction newAct(String description, String sumBefore, String sum) {
        BankAccountAction bao = new BankAccountAction();
        bao.setDate(new Date());
        bao.setDescription(description);
        bao.setSumBefore(sumBefore);
        bao.setSum(sum);
        return bao;
    }

    private void notifyApp(BusinessApp app, Billing billing) {
        LOG.debug("try notifyApp");
        CompletableFuture.runAsync(() -> {
            if (app != null && Strings.isNotBlank(app.getUrlSendResult())) {
                try {
                    String urlSendResult = app.getUrlSendResult();
                    NotifyMessage msg = new NotifyMessage();
                    msg.setId(app.getId().toString());
                    msg.setAccToken(app.getAccessApiToken().toString());
                    msg.setStatus(billing.getStatus().toString());

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    String msgAsJson = mapper.writeValueAsString(msg);
                    template.postForLocation(urlSendResult, new HttpEntity<>(msgAsJson, headers));
                    LOG.debug("notifyApp done");
                } catch (JsonProcessingException e) {
                    LOG.error("Error with DTO", e);
                } catch (Exception e) {
                    LOG.error("Error with send data", e);
                }
            }
        }, executor);
    }

    @Override
    public boolean payByCode16x(UUID clientId, String code16xCardSander, String code16xCardReceiver, String sum) {
        Client currentClient = clientRepo.findById(clientId);
        BankAccount bankAccountCurrentClient = currentClient.getBankAccounts()
            .stream().filter(e -> e.getCode16x().equals(code16xCardSander)).findFirst().orElseThrow();
        BankAccount bankAccountOtherClient = bankAccountRepo.findByCode16x(code16xCardReceiver);
        if (bankAccountOtherClient == null) throw new IllegalArgumentException("Такої картки не існує");

        String[] split = sum.split("[.,]");
        long sumBanknote = Long.parseLong(split[0]);
        int sumPenny = 0;
        if (split.length > 1) sumPenny = Integer.parseInt(split[1]);

        if (sumBanknote < 0) throw new IllegalArgumentException("Banknote < 0. it's bad");
        if (sumPenny >= 100) throw new IllegalArgumentException("Penny >= 100. it's bad");
        if (sumPenny < 0) throw new IllegalArgumentException("Penny < 0. it's bad");

        synchronized (this) {
            try {
                BankAccountAction bankAccountActionForOtherClient =
                    newAct("Отримання переказу коштів, картка надсилача: " + bankAccountCurrentClient.getCode16x(),
                        bankAccountOtherClient.getSumBanknote() + "." + bankAccountOtherClient.getSumPenny(), "+" + sum);
                BankAccountAction bankAccountActionForCurrentClient =
                    newAct("Переказ коштів, картка отримувача: " + bankAccountOtherClient.getCode16x(),
                        bankAccountCurrentClient.getSumBanknote() + "." + bankAccountCurrentClient.getSumPenny(), "-" + sum);

                long bankAccountCurrentClientNewSumBanknote = bankAccountCurrentClient.getSumBanknote() - sumBanknote;
                int bankAccountCurrentClientNewSumPenny = bankAccountCurrentClient.getSumPenny() - sumPenny;

                if (bankAccountCurrentClientNewSumPenny < 0) {
                    bankAccountCurrentClientNewSumPenny += 100;
                    bankAccountCurrentClientNewSumBanknote--;
                }
                if (bankAccountCurrentClientNewSumBanknote < 0) throw new IllegalStateException("Недостатньо коштів");

                bankAccountCurrentClient.setSumBanknote(bankAccountCurrentClientNewSumBanknote);
                bankAccountCurrentClient.setSumPenny(bankAccountCurrentClientNewSumPenny);

                long bankAccountOtherClientNewSumBanknote = bankAccountOtherClient.getSumBanknote() + sumBanknote;
                int bankAccountOtherClientNewSumPenny = bankAccountOtherClient.getSumPenny() + sumPenny;

                if (bankAccountOtherClientNewSumPenny >= 100) {
                    bankAccountOtherClientNewSumPenny -= 100;
                    bankAccountOtherClientNewSumBanknote++;
                }

                bankAccountOtherClient.setSumBanknote(bankAccountOtherClientNewSumBanknote);
                bankAccountOtherClient.setSumPenny(bankAccountOtherClientNewSumPenny);

                if (bankAccountCurrentClient.getSumBanknote() < 0) throw new IllegalStateException();
                if (bankAccountCurrentClient.getSumPenny() < 0) throw new IllegalStateException();
                if (bankAccountCurrentClient.getSumPenny() >= 100) throw new IllegalStateException();

                if (bankAccountOtherClient.getSumBanknote() < 0) throw new IllegalStateException();
                if (bankAccountOtherClient.getSumPenny() < 0) throw new IllegalStateException();
                if (bankAccountOtherClient.getSumPenny() >= 100) throw new IllegalStateException();


                bankAccountOtherClient.getBankAccountActions().add(bankAccountActionForOtherClient);
                bankAccountCurrentClient.getBankAccountActions().add(bankAccountActionForCurrentClient);

                try {
                    bankAccountRepo.save(bankAccountOtherClient);
                    bankAccountRepo.save(bankAccountCurrentClient);
                } catch (Exception es) {
                    throw new IllegalStateException("Exception with save", es);
                }

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return true;
    }

    //TODO: implement payment by iban
    @Override
    public boolean payByIban(UUID clientId, String code16xCardSander, String ibanReceiver, String sum) {
        BankAccount byIban = bankAccountRepo.findByIban(ibanReceiver);
        payByCode16x(clientId, code16xCardSander, byIban.getCode16x(), sum);
        return true;
    }

    @Override
    public Billing payByBillingId(UUID clientId, UUID billingId, String code16xCardSander) {
        Billing billing = billingRepo.findById(billingId);
        if (billing.getStatus() != BillingStatus.WAIT_PAY) {
            throw new IllegalStateException("Вже оплачено");
        }
        payByIban(clientId, code16xCardSander, billing.getBankAccountReceiver().getIban(), billing.getSum());
        billing.setStatus(BillingStatus.PAYED);
        notifyApp(billing.getBusinessApp(), billing);
        billingRepo.save(billing);
        return billing;
    }
}
