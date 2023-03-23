package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIban(String iban);

    Optional<BankAccount> findByCode16x(String code16x);

    @Query("select (count(b) > 0) from BankAccount b where b.code16x = ?1")
    boolean existByCode16x(String code16x);

    @Query("select (count(b) > 0) from BankAccount b where b.iban = ?1")
    boolean existByIban(String iban);


}