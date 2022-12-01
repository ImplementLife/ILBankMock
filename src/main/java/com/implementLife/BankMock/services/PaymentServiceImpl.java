package com.implementLife.BankMock.services;

import com.implementLife.BankMock.data.entity.*;
import com.implementLife.BankMock.services.interfaces.BankAccountRepo;
import com.implementLife.BankMock.services.interfaces.BillingRepo;
import com.implementLife.BankMock.services.interfaces.ClientRepo;
import com.implementLife.BankMock.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private BankAccountRepo bankAccountRepo;

    private BankAccountAction newAct(String description, String sumBefore, String sum) {
        BankAccountAction bao = new BankAccountAction();
        bao.setId(UUID.randomUUID());
        bao.setDate(new Date());
        bao.setDescription(description);
        bao.setSumBefore(sumBefore);
        bao.setSum(sum);
        return bao;
    }

    @Override
    public boolean payByCode16x(UUID clientId, String code16xCardSander, String code16xCardReceiver, String sum) {
        Client currentClient = clientRepo.getById(clientId);
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
        Billing byId = billingRepo.findById(billingId);
        if (byId.getStatus() != BillingStatus.WAIT_PAY) {
            throw new IllegalStateException("Вже оплачено");
        }
        payByIban(clientId, code16xCardSander, byId.getBankAccountReceiver().getIban(), byId.getSum());
        byId.setStatus(BillingStatus.PAYED);
        return byId;
    }
}
