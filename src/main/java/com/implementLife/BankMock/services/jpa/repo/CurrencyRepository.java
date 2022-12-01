package com.implementLife.BankMock.services.jpa.repo;

import com.implementLife.BankMock.data.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}