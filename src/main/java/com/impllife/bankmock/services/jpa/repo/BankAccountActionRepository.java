package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.BankAccountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountActionRepository extends JpaRepository<BankAccountAction, UUID> {
}