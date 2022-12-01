package com.implementLife.BankMock.services.jpa.repo;

import com.implementLife.BankMock.data.entity.BankAccountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountActionRepository extends JpaRepository<BankAccountAction, UUID> {
}