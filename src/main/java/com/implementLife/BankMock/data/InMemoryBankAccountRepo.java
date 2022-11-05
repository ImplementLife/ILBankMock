package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class InMemoryBankAccountRepo implements BankAccountRepo {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryBankAccountRepo.class);
    private Map<UUID, BankAccount> mapById;
    private Map<String, BankAccount> mapByCode16x;

    public InMemoryBankAccountRepo() {
        mapById = new TreeMap<>();
        mapByCode16x = new TreeMap<>();
    }

    private UUID createPrimaryId() {
        UUID id = UUID.randomUUID();
        if (mapById.containsKey(id)) {
            return createPrimaryId();
        } else {
            return id;
        }
    }

    private String createPrimaryCode16x() {
        String code = String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L));
        if (mapByCode16x.containsKey(code)) {
            return createPrimaryCode16x();
        } else {
            return code;
        }
    }

    private void save(BankAccount bankAccount) {
        mapById.put(bankAccount.getId(), bankAccount);
        mapByCode16x.put(bankAccount.getCode16x(), bankAccount);
    }

    @Override
    public BankAccount createBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(createPrimaryId());
        bankAccount.setCode16x(createPrimaryCode16x());
        bankAccount.setCodeCvv(String.format("%03d", (int) (Math.random() * 999L)));
        bankAccount.setSumBanknote(320);
        bankAccount.setSumPenny(30);
        bankAccount.setName("Картка Універсальна");
        bankAccount.setDateCreate(new Date());
        bankAccount.setBankAccountActions(new LinkedList<>());
        save(bankAccount);
        LOG.info("\t" + bankAccount.getCode16x());
        return bankAccount;
    }

    @Override
    public BankAccount find(UUID id) {
        return mapById.get(id);
    }

    @Override
    public BankAccount find(String code16x) {
        return mapByCode16x.get(code16x);
    }
}
