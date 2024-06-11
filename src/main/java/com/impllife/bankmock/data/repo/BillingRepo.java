package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingRepo extends JpaRepository<Billing, UUID> {
}