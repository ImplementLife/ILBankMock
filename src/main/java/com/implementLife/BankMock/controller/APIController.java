package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.controller.dto.ClientResponse;
import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import com.implementLife.BankMock.entity.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
public interface APIController {

    @Operation(responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "403")
    })
    @PostMapping("createClientAccount")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> createClientAccount(@RequestBody Client personalInfo);

    @Operation(responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "302", description = "redirect to login"),
        @ApiResponse(responseCode = "403")
    })
    @GetMapping("getClientAccountInfo")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ClientResponse> getClientAccountInfo(String phone);


    @Operation(responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "403")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("createBankAccount")
    void createBankAccount(String clientId, String CurrencyId);


    @Operation(responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "403")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("updatePersonalInfo")
    ResponseEntity<String> updatePersonalInfo(Client personalInfo);


    @Operation(responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "403")
    })
    @PostMapping("createBilling")
    @ResponseStatus(HttpStatus.CREATED)
    void createBilling(@Valid @RequestBody CreateBillingRequest request);


    @Operation(responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "403")
    })
    @PostMapping("payBilling")
    void payBilling(@Valid @RequestBody PayBillingRequest request);

    
}
