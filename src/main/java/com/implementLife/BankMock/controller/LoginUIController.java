package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.data.ClientRepo;
import com.implementLife.BankMock.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginUIController {
    @Autowired
    private ClientRepo clientRepo;

    @PostMapping("/reg")
    public String registration(@ModelAttribute Client client, @RequestParam(defaultValue = "/") String referer) {
        Client savedClient = clientRepo.save(client);
        return "redirect:" + referer;
    }


}
