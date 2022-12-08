package com.implementLife.BankMock.services.jpa.repo;

import com.implementLife.BankMock.data.entity.BusinessApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessAppRepository extends JpaRepository<BusinessApp, UUID> {
}