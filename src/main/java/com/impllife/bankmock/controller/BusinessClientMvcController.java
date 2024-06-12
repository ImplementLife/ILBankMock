package com.impllife.bankmock.controller;

import com.impllife.bankmock.data.entity.BusinessApp;
import com.impllife.bankmock.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/business")
public class BusinessClientMvcController extends BaseMvcController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/requestBankAccount")
    public String requestBusinessBankAccount(Model model) {
        doWithErrHandle(() -> clientService.requestBusinessBankAccount(getSec().getClient()), model, "Заявку оброблено");
        return "user/result";
    }

    @GetMapping("/registerBusinessApp")
    public String registerBusinessApp(Model model) {
        model.addAttribute("bankAccounts", getClient().getBankAccounts());
        return "user/business/registerApp";
    }

    @PostMapping("/registerBusinessApp")
    public String registerBusinessApp(@ModelAttribute BusinessApp app, Model model) {
        doWithErrHandle(() -> clientService.registerBusinessApp(getClient(), app), model, "Заявку оброблено");
        return "user/result";
    }
}
