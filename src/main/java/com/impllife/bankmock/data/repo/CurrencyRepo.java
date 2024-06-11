package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {
}