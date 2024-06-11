package com.impllife.bankmock.services;

import com.impllife.bankmock.data.entity.BankAccount;
import com.impllife.bankmock.data.entity.BankAccountCreateOrder;
import com.impllife.bankmock.data.entity.BankAccountTemplate;
import com.impllife.bankmock.data.entity.Client;
import com.impllife.bankmock.data.repo.BankAccountCreateOrderRepo;
import com.impllife.bankmock.data.repo.BankAccountRepo;
import com.impllife.bankmock.services.interfaces.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepo bankAccountRepo;
    @Autowired
    private BankAccountCreateOrderRepo bankAccountCreateOrderRepo;

    private String createPrimaryCode16x() {
        String code = String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L));
        if (bankAccountRepo.existByCode16x(code)) {
            return createPrimaryCode16x();
        } else {
            return code;
        }
    }

    private String createPrimaryIban() {
        String code = "UA" +
            String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L)) +
            String.format("%016d", (long) (Math.random() * 9999_9999_999L));
        if (bankAccountRepo.existByIban(code)) {
            return createPrimaryIban();
        } else {
            return code;
        }
    }

    @Override
    public boolean save(BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
        return true;
    }

    @Override
    public BankAccount createBankAccount(BankAccountTemplate template) {
        BankAccount bankAccount = new BankAccount();
        //if ("personal".equals(template.getName())) {
        bankAccount.setCode16x(createPrimaryCode16x());
        bankAccount.setCodeCvv(String.format("%03d", (int) (Math.random() * 999L)));
        bankAccount.setName("Картка Універсальна");
        //} else if ("business".equals(template.getName())) {
        bankAccount.setIban(createPrimaryIban());
        //}
        bankAccount.setDateCreate(new Date());
        bankAccount.setCurrency(template.getCurrency());
        bankAccount.setBankAccountActions(new LinkedList<>());

        return bankAccountRepo.save(bankAccount);
    }

    @Override
    public BankAccount findById(UUID id) {
        return bankAccountRepo.findById(id).orElseThrow();
    }

    @Override
    public BankAccount findByIban(String iban) {
        return bankAccountRepo.findByIban(iban).orElseThrow();
    }

    @Override
    public BankAccount findByCode16x(String code16x) {
        return bankAccountRepo.findByCode16x(code16x).orElseThrow();
    }

    @Override
    public BankAccountCreateOrder findOrderById(UUID id) {
        return bankAccountCreateOrderRepo.findById(id).orElseThrow();
    }

    @Override
    public List<BankAccountCreateOrder> findAllOrdersOnReview() {
        return bankAccountCreateOrderRepo.findAllOnReview();
    }

    @Override
    public BankAccountCreateOrder createOrder(Client client) {
        BankAccountCreateOrder order = new BankAccountCreateOrder();
        order.setClient(client);
        order.setStatus("onReview");
        bankAccountCreateOrderRepo.saveAndFlush(order);
        return order;
    }

    @Override
    public void saveOrder(BankAccountCreateOrder order) {
        bankAccountCreateOrderRepo.saveAndFlush(order);
    }
}
