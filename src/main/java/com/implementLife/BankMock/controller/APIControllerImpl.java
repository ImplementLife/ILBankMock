package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.controller.dto.ClientResponse;
import com.implementLife.BankMock.controller.dto.CreateBillingRequest;
import com.implementLife.BankMock.controller.dto.PayBillingRequest;
import com.implementLife.BankMock.data.ClientRepo;
import com.implementLife.BankMock.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIControllerImpl implements APIController {
    @Autowired
    private ClientRepo clientRepo;

    @Override
    public ResponseEntity<String> createClientAccount(Client client) {
        clientRepo.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @Override
    public ResponseEntity<ClientResponse> getClientAccountInfo(String phone) {
        return ResponseEntity.ok(clientRepo.getByPhone(phone));
    }

    @Override
    public void createBankAccount(String clientId, String CurrencyId) {

    }

    @Override
    public ResponseEntity<String> updatePersonalInfo(Client client) {
        clientRepo.save(client);
        return ResponseEntity.ok("ok");
    }

    @Override
    public void createBilling(CreateBillingRequest request) {

    }

    @Override
    public void payBilling(PayBillingRequest request) {

    }

}
