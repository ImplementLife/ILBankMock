package com.impllife.bankmock.services;

import com.impllife.bankmock.data.entity.*;
import com.impllife.bankmock.data.entity.security.Role;
import com.impllife.bankmock.data.repo.BusinessAppRepo;
import com.impllife.bankmock.data.repo.ClientRepo;
import com.impllife.bankmock.data.repo.CurrencyRepo;
import com.impllife.bankmock.services.interfaces.BankAccountService;
import com.impllife.bankmock.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BusinessAppRepo businessAppRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Override
    public List<BankAccountAction> getHistory(UUID clientId, UUID bankAccountId) {
        Client byId = clientRepo.findById(clientId).get();
        return byId.getBankAccounts().stream().filter(e -> e.getId().equals(bankAccountId)).findFirst().orElseThrow().getBankAccountActions();
    }

    @Override
    public boolean createOrder(Client client) {
        bankAccountService.createOrder(client);
        return true;
    }

    private void processingCreateOrder(BankAccountCreateOrder order) {
        Client client = clientRepo.findById(order.getClient().getId()).get();

        BankAccountTemplate template = new BankAccountTemplate();
        template.setName("personal");
        template.setCurrency(currencyRepo.findById(1L).orElseThrow());

        client.getBankAccounts().add(bankAccountService.createBankAccount(template));
        clientRepo.save(client);
    }

    @Override
    public boolean processingCreateOrder(UUID idOrder, String action) {
        BankAccountCreateOrder order = bankAccountService.findOrderById(idOrder);
        if ("approve".equals(action)) {
            order.setStatus(action);
            processingCreateOrder(order);
        } else if ("dismiss".equals(action)) {
            order.setStatus(action);
        } else {
            throw new IllegalArgumentException("Not exist action");
        }
        bankAccountService.saveOrder(order);
        return true;
    }

    @Override
    public void addBusinessRole(Client client) {
        String roles = client.getRoles();
        if (!roles.contains("" + Role.BUSINESS_USER.getId())) {
            client.setRoles(roles + Role.BUSINESS_USER.getId());
        }
        clientRepo.save(client);
    }

    @Override
    public void registerBusinessApp(Client client, BusinessApp app) {
        Client clientById = clientRepo.findById(client.getId()).get();
        app.setClient(clientById);
        app.setAccessApiToken(UUID.randomUUID());
//        if (client.getBusinessApps() == null) client.setBusinessApps(new LinkedList<>());
//        client.getBusinessApps().add(app);

        businessAppRepo.save(app);
    }

    @Override
    public void requestBusinessBankAccount(Client client) {
        Client clientById = clientRepo.findById(client.getId()).get();

        BankAccountTemplate template = new BankAccountTemplate();
        template.setName("business");
        template.setCurrency(currencyRepo.findById(1L).orElseThrow(() -> new IllegalStateException("Currency is not defined")));

        BankAccount bankAccount = bankAccountService.createBankAccount(template);

        clientById.getBankAccounts().add(bankAccount);
        bankAccount.setClient(clientById);
        bankAccountService.save(bankAccount);
        clientRepo.save(clientById);
    }
}
