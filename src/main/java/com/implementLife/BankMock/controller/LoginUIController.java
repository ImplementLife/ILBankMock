package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.data.ClientRepo;
import com.implementLife.BankMock.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginUIController {
    @Autowired
    private ClientRepo clientRepo;

    @ResponseBody
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody Client client) {
        Client savedClient = clientRepo.save(client);
        return ResponseEntity.ok("done");
    }


}
