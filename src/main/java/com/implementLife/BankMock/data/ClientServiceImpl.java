package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BankAccount;
import com.implementLife.BankMock.entity.BankAccountAction;
import com.implementLife.BankMock.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Override
    public boolean pay(UUID clientId, String code16xCurrentClient, String code16xOtherClient, String sum) {
        Client currentClient = clientRepo.getById(clientId);
        BankAccount bankAccountCurrentClient = currentClient.getBankAccounts()
            .stream().filter(e -> e.getCode16x().equals(code16xCurrentClient)).findFirst().orElseThrow();
        BankAccount bankAccountOtherClient = bankAccountRepo.find(code16xOtherClient);
        if (bankAccountOtherClient == null) throw new IllegalArgumentException("Такої картки не існує");

        String[] split = sum.split("\\.");
        long sumBanknote = Long.parseLong(split[0]);
        int sumPenny = Integer.parseInt(split[1]);

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
    public List<BankAccountAction> getHistory(UUID clientId, UUID bankAccountId) {
        Client byId = clientRepo.getById(clientId);
        return byId.getBankAccounts().stream().filter(e -> e.getId().equals(bankAccountId)).findFirst().orElseThrow().getBankAccountActions();
    }
}
