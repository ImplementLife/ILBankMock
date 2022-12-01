package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.data.entity.security.Role;
import com.implementLife.BankMock.services.interfaces.ClientRepo;
import com.implementLife.BankMock.data.entity.Client;
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
        client.setRoles(String.valueOf((Role.USER.getId())));
        Client savedClient = clientRepo.save(client);
        return "redirect:" + referer;
    }


}
