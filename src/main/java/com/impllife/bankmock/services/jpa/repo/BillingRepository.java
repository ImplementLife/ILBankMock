package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billing, UUID> {
}