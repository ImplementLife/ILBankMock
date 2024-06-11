package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.BusinessApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessAppRepo extends JpaRepository<BusinessApp, UUID> {
}