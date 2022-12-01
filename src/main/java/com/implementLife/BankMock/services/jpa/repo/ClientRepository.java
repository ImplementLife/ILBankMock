package com.implementLife.BankMock.services.jpa.repo;

import com.implementLife.BankMock.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByPhoneNumber(String phoneNumber);
}