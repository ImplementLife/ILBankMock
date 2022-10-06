package com.implementLife.BankMock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface APIController {
    @PostMapping("createClientAccount")
    void createClientAccount();
    @PostMapping("createBankAccount")
    void createBankAccount();
    @PutMapping("updatePersonalInfo")
    void updatePersonalInfo();
    
}
