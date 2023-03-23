package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.BusinessApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessAppRepository extends JpaRepository<BusinessApp, UUID> {
}