package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.BankAccountTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountTemplateRepo extends JpaRepository<BankAccountTemplate, UUID> {
}