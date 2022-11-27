package com.implementLife.BankMock.controller;

import com.implementLife.BankMock.data.entity.security.ClientSec;
import com.implementLife.BankMock.data.entity.security.Role;
import com.implementLife.BankMock.data.dto.PayRequest;
import com.implementLife.BankMock.services.interfaces.BankAccountRepo;
import com.implementLife.BankMock.services.interfaces.BillingRepo;
import com.implementLife.BankMock.services.interfaces.ClientService;
import com.implementLife.BankMock.services.interfaces.PaymentService;
import com.implementLife.BankMock.data.entity.BankAccountAction;
import com.implementLife.BankMock.data.entity.Billing;
import com.implementLife.BankMock.data.entity.BusinessApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MvcController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private BankAccountRepo bankAccountRepo;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BillingRepo billingRepo;

    @GetMapping("/")
    public String main() {
        return "redirect:/profile";
    }

    private ClientSec getSec() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ClientSec) authentication.getPrincipal();
    }
    private void err(Runnable run, Model model, String message) {
        try {
            run.run();
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }
    }
    private void err(Runnable action, Model model, Runnable reactionOnErr) {

    }

    @GetMapping("/profile")
    public String profile(Model model) {
        ClientSec clientSec = getSec();

        clientSec.getAuthorities().forEach(e -> model.addAttribute(e.getAuthority(), true));

        model.addAttribute("client", clientSec.getClient());
        if (clientSec.getAuthorities().contains(Role.MANAGER)) {
            model.addAttribute("ordersOnReview", bankAccountRepo.getAllOrdersOnReview());
        }
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
        model.addAttribute("bankAccounts", clientSec.getClient().getBankAccounts());
        return "user/pay";
    }

    @GetMapping("/profile/payBilling")
    public String payBilling(Model model, @RequestParam String billingId) {
        ClientSec clientSec = getSec();
        model.addAttribute("bankAccounts", clientSec.getClient().getBankAccounts());

        Billing billingInfo = billingRepo.findById(UUID.fromString(billingId));
        model.addAttribute("billingInfo", billingInfo);
        return "user/payBilling";
    }

    @PostMapping("/profile/submitPayBilling")
    public String submitPayBilling(Model model, @RequestParam UUID billingId, @RequestParam String code16xCurrentClient) {
        ClientSec clientSec = getSec();
        model.addAttribute("bankAccounts", clientSec.getClient().getBankAccounts());

        try {
            Billing billing = paymentService.payByBillingId(clientSec.getClient().getId(), billingId, code16xCurrentClient);
            model.addAttribute("message", "Платіж успішно проведено");
            model.addAttribute("completePaymentLink", billing.getCompletePaymentLink());
        } catch (Exception ignored) {
            model.addAttribute("message", "Платіж НЕ вдалося обробити");
        }
        return "user/payBillingResult";
    }

    @PostMapping("/profile/pay")
    public String pay(@ModelAttribute PayRequest payRequest, Model model) {
        err(() -> paymentService.payByCode16x(
            getSec().getClient().getId(),
            payRequest.getCode16xCurrentClient(),
            payRequest.getCode16xOtherClient(),
            payRequest.getSum()), model, "Платіж успішно оброблено");
        return "user/result";
    }

    @GetMapping("/profile/requestBankAccount")
    public String requestOrder(Model model) {
        err(() -> clientService.createOrder(getSec().getClient()), model, "Заявку надіслано");
        return "user/result";
    }

    @GetMapping("/manager/processingOrder")
    public String getListOrders(@RequestParam String id, @RequestParam String act, Model model) {
        err(() -> clientService.processingCreateOrder(UUID.fromString(id), act), model, "Заявку оброблено");
        return "user/result";
    }

    @GetMapping("/profile/requestRegisterBusinessAccount")
    public String registerBusinessAccount(Model model) {
        err(() -> clientService.addBusinessRole(getSec().getClient()), model, "Заявку оброблено");
        return "user/result";
    }

    @GetMapping("/profile/business/registerBusinessApp")
    public String registerBusinessApp(Model model) {
        model.addAttribute("bankAccounts", getSec().getClient().getBankAccounts());
        return "user/business/registerApp";
    }

    @PostMapping("/profile/business/registerBusinessApp")
    public String registerBusinessApp(@ModelAttribute BusinessApp app, Model model) {
        err(() -> clientService.registerBusinessApp(getSec().getClient(), app), model, "Заявку оброблено");
        return "user/result";
    }
}
