package com.implementLife.BankMock.data;

import com.implementLife.BankMock.config.security.Role;
import com.implementLife.BankMock.entity.*;
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

    @Override
    public boolean createOrder(Client client) {
        bankAccountRepo.createOrder(client);
        return true;
    }

    private boolean bankAccountCreateOrder(BankAccountCreateOrder order) {
        Client client = clientRepo.getById(order.getClient().getId());

        client.getBankAccounts().add(bankAccountRepo.createBankAccount());
        return true;
    }

    @Override
    public boolean processingCreateOrder(UUID idOrder, String action) {
        BankAccountCreateOrder orderById = bankAccountRepo.getOrderById(idOrder);
        if ("approve".equals(action)) {
            orderById.setStatus(action);
            bankAccountCreateOrder(orderById);
        } else if ("dismiss".equals(action)) {
            orderById.setStatus(action);
        } else {
            throw new IllegalArgumentException("Not exist action");
        }
        return true;
    }

    @Override
    public void addBusinessRole(Client client) {
        String roles = client.getRoles();
        if (!roles.contains("" + Role.BUSINESS_USER.getId())) {
            client.setRoles(roles + Role.BUSINESS_USER.getId());
        }
    }

    @Override
    public void registerBusinessApp(Client client, String name) {
        BusinessApp businessApp = new BusinessApp();
        businessApp.setId(UUID.randomUUID());
        businessApp.setAccessApiToken(UUID.randomUUID());
        businessApp.setName(name);
        businessApp.setClient(client);
        client.getBusinessApps().add(businessApp);
    }
}
