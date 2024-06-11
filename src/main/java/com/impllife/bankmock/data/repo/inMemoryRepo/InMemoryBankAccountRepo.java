//package com.impllife.bankmock.services.inMemoryRepo;
//
//import com.impllife.bankmock.data.entity.*;
//import com.impllife.bankmock.data.entity.Currency;
//import com.impllife.bankmock.services.interfaces.BankAccountRepo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class InMemoryBankAccountRepo implements BankAccountRepo {
//    private static final Logger LOG = LoggerFactory.getLogger(InMemoryBankAccountRepo.class);
//    private final Map<UUID, BankAccount> bankAccountsById = new TreeMap<>();
//    private final Map<String, BankAccount> bankAccountsByCode16x = new TreeMap<>();
//    private final Map<String, BankAccount> bankAccountsByIban = new TreeMap<>();
//
//    private final Map<UUID, BankAccountCreateOrder> ordersById = new TreeMap<>();
//    private final Map<UUID, List<BankAccountCreateOrder>> ordersByClientId = new TreeMap<>();
//
//    private UUID createPrimaryId() {
//        UUID id = UUID.randomUUID();
//        if (bankAccountsById.containsKey(id)) {
//            return createPrimaryId();
//        } else {
//            return id;
//        }
//    }
//
//    private String createPrimaryCode16x() {
//        String code = String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L));
//        if (bankAccountsByCode16x.containsKey(code)) {
//            return createPrimaryCode16x();
//        } else {
//            return code;
//        }
//    }
//
//    private String createPrimaryIban() {
//        String code = "UA" +
//            String.format("%016d", (long) (Math.random() * 9999_9999_9999_9999L)) +
//            String.format("%016d", (long) (Math.random() * 9999_9999_999L));
//        if (bankAccountsByIban.containsKey(code)) {
//            return createPrimaryIban();
//        } else {
//            return code;
//        }
//    }
//
//    @Override
//    public boolean save(BankAccount bankAccount) {
//        bankAccountsById.put(bankAccount.getId(), bankAccount);
//        bankAccountsByCode16x.put(bankAccount.getCode16x(), bankAccount);
//        bankAccountsByIban.put(bankAccount.getIban(), bankAccount);
//        return true;
//    }
//
//    @Override
//    public BankAccount createBankAccount(BankAccountTemplate template) {
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setId(createPrimaryId());
//        bankAccount.setCode16x(createPrimaryCode16x());
//        bankAccount.setCodeCvv(String.format("%03d", (int) (Math.random() * 999L)));
//        bankAccount.setIban(createPrimaryIban());
//        bankAccount.setName("Картка Універсальна");
//        bankAccount.setDateCreate(new Date());
//        bankAccount.setCurrency(new Currency("$"));
//        bankAccount.setBankAccountActions(new LinkedList<>());
//        save(bankAccount);
//        LOG.info("\t" + bankAccount.getCode16x());
//        return bankAccount;
//    }
//
//    @Override
//    public BankAccount findById(UUID id) {
//        return bankAccountsById.get(id);
//    }
//    @Override
//    public BankAccount findByIban(String iban) {
//        return bankAccountsByIban.get(iban);
//    }
//
//    public BankAccount findByCode16x(String code16x) {
//        return bankAccountsByCode16x.get(code16x);
//    }
//
//    @Override
//    public BankAccountCreateOrder findOrderById(UUID id) {
//        return ordersById.get(id);
//    }
//
//    @Override
//    public List<BankAccountCreateOrder> findAllOrdersOnReview() {
//        return ordersById.values().stream().filter(e -> "onReview".equals(e.getStatus())).collect(Collectors.toList());
//    }
//
//    @Override
//    public BankAccountCreateOrder createOrder(Client client) {
//        BankAccountCreateOrder order = new BankAccountCreateOrder();
//        order.setId(UUID.randomUUID());
//        order.setClient(client);
//        order.setStatus("onReview");
//        saveOrder(order);
//        return order;
//    }
//
//    @Override
//    public void saveOrder(BankAccountCreateOrder order) {
//        ordersById.put(order.getId(), order);
//    }
//}
