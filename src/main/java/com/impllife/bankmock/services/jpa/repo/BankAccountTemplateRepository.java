package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.BankAccountTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountTemplateRepository extends JpaRepository<BankAccountTemplate, UUID> {
}