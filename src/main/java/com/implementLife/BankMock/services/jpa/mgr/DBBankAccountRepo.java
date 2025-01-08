package com.implementLife.BankMock.services.jpa.mgr;

import com.implementLife.BankMock.data.entity.*;
import com.implementLife.BankMock.services.interfaces.BankAccountRepo;
import com.implementLife.BankMock.services.jpa.repo.BankAccountCreateOrderRepository;
import com.implementLife.BankMock.services.jpa.repo.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class DBBankAccountRepo implements BankAccountRepo {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountCreateOrderRepository bankAccountCreateOrderRepository;

    private String createPrimaryCode16x() {
        String code = String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L));
        if (bankAccountRepository.existByCode16x(code)) {
            return createPrimaryCode16x();
        } else {
            return code;
        }
    }

    private String createPrimaryIban() {
        String code = "UA" +
            String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L)) +
            String.format("%016d", (long) (Math.random() * 9999_9999_999L));
        if (bankAccountRepository.existByIban(code)) {
            return createPrimaryIban();
        } else {
            return code;
        }
    }

    @Override
    public boolean save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return true;
    }

    @Override
    public BankAccount createBankAccount(BankAccountTemplate template) {
        BankAccount bankAccount = new BankAccount();
        if ("personal".equals(template.getName())) {
            bankAccount.setCode16x(createPrimaryCode16x());
            bankAccount.setCodeCvv(String.format("%03d", (int) (Math.random() * 999L)));
            bankAccount.setName("Картка Універсальна");
        } else if ("business".equals(template.getName())) {
            bankAccount.setIban(createPrimaryIban());
        }
        bankAccount.setDateCreate(new Date());
        bankAccount.setCurrency(template.getCurrency());
        bankAccount.setBankAccountActions(new LinkedList<>());
        bankAccount.setClient(bankAccount.getClient());

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount findById(UUID id) {
        return bankAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public BankAccount findByIban(String iban) {
        return bankAccountRepository.findByIban(iban).orElseThrow();
    }

    @Override
    public BankAccount findByCode16x(String code16x) {
        return bankAccountRepository.findByCode16x(code16x).orElseThrow();
    }

    @Override
    public BankAccountCreateOrder findOrderById(UUID id) {
        return bankAccountCreateOrderRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BankAccountCreateOrder> findAllOrdersOnReview() {
        return bankAccountCreateOrderRepository.findAllOnReview();
    }

    @Override
    public BankAccountCreateOrder createOrder(Client client) {
        BankAccountCreateOrder order = new BankAccountCreateOrder();
        order.setClient(client);
        order.setStatus("onReview");
        bankAccountCreateOrderRepository.saveAndFlush(order);
        return order;
    }

    @Override
    public void saveOrder(BankAccountCreateOrder order) {
        bankAccountCreateOrderRepository.saveAndFlush(order);
    }
}
