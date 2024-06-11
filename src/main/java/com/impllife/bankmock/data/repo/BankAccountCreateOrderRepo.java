package com.impllife.bankmock.data.repo;

import com.impllife.bankmock.data.entity.BankAccountCreateOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BankAccountCreateOrderRepo extends JpaRepository<BankAccountCreateOrder, UUID> {
    @Query("select b from BankAccountCreateOrder b where b.status = 'onReview'")
    List<BankAccountCreateOrder> findAllOnReview();

}