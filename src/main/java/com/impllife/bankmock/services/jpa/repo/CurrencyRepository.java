package com.impllife.bankmock.services.jpa.repo;

import com.impllife.bankmock.data.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}