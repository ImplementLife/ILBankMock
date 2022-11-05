package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.config.security.ClientSec;
import com.implementLife.BankMock.controller.dto.PayRequest;
import com.implementLife.BankMock.data.ClientService;
import com.implementLife.BankMock.entity.BankAccountAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class MvcController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String main() {
        return "redirect:/profile";
    }

    private ClientSec getSec() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ClientSec) authentication.getPrincipal();
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        ClientSec clientSec = getSec();

        clientSec.getAuthorities().forEach(e -> model.addAttribute(e.getAuthority(), true));
        model.addAttribute("client", clientSec.getClient());
        return "user/profile";
    }

    @GetMapping("/profile/getBankAccountHistory")
    public String bankAccountHistory(@RequestParam String id, Model model) {
        ClientSec clientSec = getSec();
        UUID uuid = UUID.fromString(id);
        List<BankAccountAction> history = clientService.getHistory(clientSec.getClient().getId(), uuid);
        model.addAttribute("history", history);
        return "user/bankAccountHistory";
    }

    @GetMapping("/profile/pay")
    public String bankAccountHistory(Model model) {
        ClientSec clientSec = getSec();
        return "user/pay";
    }

    @PostMapping("/profile/pay")
    public String pay(@ModelAttribute PayRequest payRequest, Model model) {
        try {
            clientService.pay(getSec().getClient().getId(), payRequest.getCode16xCurrentClient(), payRequest.getCode16xOtherClient(), payRequest.getSum());
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "user/payResult";
    }
}
