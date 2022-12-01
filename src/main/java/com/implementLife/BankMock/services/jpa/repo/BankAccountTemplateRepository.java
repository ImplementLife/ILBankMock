package com.implementLife.BankMock.services.jpa.repo;

import com.implementLife.BankMock.data.entity.BankAccountTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountTemplateRepository extends JpaRepository<BankAccountTemplate, UUID> {
}