package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByPhoneNumber(String phoneNumber);
}