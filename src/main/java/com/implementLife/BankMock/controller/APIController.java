package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import com.implementLife.BankMock.entity.Currency;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
public interface APIController {

    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "403")

    @PostMapping("createClientAccount")
    @ResponseStatus(HttpStatus.CREATED)
    void createClientAccount();


    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "403")

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("createBankAccount")
    void createBankAccount(String clientId, String CurrencyId);


    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "403")

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("updatePersonalInfo")
    void updatePersonalInfo();


    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "403")

    @PostMapping("createBilling")
    @ResponseStatus(HttpStatus.CREATED)
    void createBilling(@Valid @RequestBody CreateBillingRequest request);

    @ApiResponse(responseCode = "403")
    @PostMapping("payBilling")
    void payBilling(@Valid @RequestBody PayBillingRequest request);

    
}
