package com.impllife.bankmock.services;

import com.impllife.bankmock.data.entity.*;
import com.impllife.bankmock.data.entity.security.Role;
import com.impllife.bankmock.services.interfaces.BankAccountRepo;
import com.impllife.bankmock.services.interfaces.BusinessAppRepo;
import com.impllife.bankmock.services.interfaces.ClientRepo;
import com.impllife.bankmock.services.interfaces.ClientService;
import com.impllife.bankmock.services.jpa.repo.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BankAccountRepo bankAccountRepo;
    @Autowired
    private BusinessAppRepo businessAppRepo;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public List<BankAccountAction> getHistory(UUID clientId, UUID bankAccountId) {
        Client byId = clientRepo.findById(clientId);
        return byId.getBankAccounts().stream().filter(e -> e.getId().equals(bankAccountId)).findFirst().orElseThrow().getBankAccountActions();
    }

    @Override
    public boolean createOrder(Client client) {
        bankAccountRepo.createOrder(client);
        return true;
    }

    private void processingCreateOrder(BankAccountCreateOrder order) {
        Client client = clientRepo.findById(order.getClient().getId());

        BankAccountTemplate template = new BankAccountTemplate();
        template.setName("personal");
        template.setCurrency(currencyRepository.findById(1L).orElseThrow());

        client.getBankAccounts().add(bankAccountRepo.createBankAccount(template));
        clientRepo.save(client);
    }

    @Override
    public boolean processingCreateOrder(UUID idOrder, String action) {
        BankAccountCreateOrder order = bankAccountRepo.findOrderById(idOrder);
        if ("approve".equals(action)) {
            order.setStatus(action);
            processingCreateOrder(order);
        } else if ("dismiss".equals(action)) {
            order.setStatus(action);
        } else {
            throw new IllegalArgumentException("Not exist action");
        }
        bankAccountRepo.saveOrder(order);
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
        Client clientById = clientRepo.findById(client.getId());
        app.setClient(clientById);
        app.setAccessApiToken(UUID.randomUUID());
//        if (client.getBusinessApps() == null) client.setBusinessApps(new LinkedList<>());
//        client.getBusinessApps().add(app);

        businessAppRepo.save(app);
    }

    @Override
    public void requestBusinessBankAccount(Client client) {
        Client clientById = clientRepo.findById(client.getId());

        BankAccountTemplate template = new BankAccountTemplate();
        template.setName("business");
        template.setCurrency(currencyRepository.findById(1L).orElseThrow());

        BankAccount bankAccount = bankAccountRepo.createBankAccount(template);

        clientById.getBankAccounts().add(bankAccount);
        bankAccount.setClient(clientById);
        bankAccountRepo.save(bankAccount);
        clientRepo.save(clientById);
    }
}
