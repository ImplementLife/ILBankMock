package com.implementLife.BankMock.services;

import com.implementLife.BankMock.data.entity.security.Role;
import com.implementLife.BankMock.data.entity.BankAccountAction;
import com.implementLife.BankMock.data.entity.BankAccountCreateOrder;
import com.implementLife.BankMock.data.entity.BusinessApp;
import com.implementLife.BankMock.data.entity.Client;
import com.implementLife.BankMock.services.interfaces.BankAccountRepo;
import com.implementLife.BankMock.services.interfaces.BusinessAppRepo;
import com.implementLife.BankMock.services.interfaces.ClientRepo;
import com.implementLife.BankMock.services.interfaces.ClientService;
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
    public void registerBusinessApp(Client client, BusinessApp app) {
        app.setClient(client);
        client.getBusinessApps().add(app);
        businessAppRepo.save(app);
    }
}
