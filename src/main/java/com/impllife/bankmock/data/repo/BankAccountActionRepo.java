package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.BankAccountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountActionRepo extends JpaRepository<BankAccountAction, UUID> {
}