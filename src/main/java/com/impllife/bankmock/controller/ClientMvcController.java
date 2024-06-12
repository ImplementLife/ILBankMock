package com.impllife.bankmock.controller;

import com.impllife.bankmock.data.dto.PayRequest;
import com.impllife.bankmock.data.entity.BankAccountAction;
import com.impllife.bankmock.data.entity.Billing;
import com.impllife.bankmock.data.entity.BusinessApp;
import com.impllife.bankmock.data.entity.Client;
import com.impllife.bankmock.data.entity.security.ClientSec;
import com.impllife.bankmock.data.entity.security.Role;
import com.impllife.bankmock.data.repo.BillingRepo;
import com.impllife.bankmock.services.interfaces.BankAccountService;
import com.impllife.bankmock.services.interfaces.ClientService;
import com.impllife.bankmock.services.interfaces.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class ClientMvcController extends BaseMvcController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BillingRepo billingRepo;

    @GetMapping("/")
    public String main() {
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        ClientSec clientSec = getSec();

        clientSec.getAuthorities().forEach(e -> model.addAttribute(e.getAuthority(), true));

        model.addAttribute("client", getClient());
        if (clientSec.getAuthorities().contains(Role.MANAGER)) {
            model.addAttribute("ordersOnReview", bankAccountService.findAllOrdersOnReview());
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
        model.addAttribute("bankAccounts", getClient().getBankAccounts());
        return "user/pay";
    }

    @GetMapping("/profile/payBilling")
    public String payBilling(Model model, @RequestParam String billingId) {
        model.addAttribute("bankAccounts", getClient().getBankAccounts());

        Billing billingInfo = billingRepo.findById(UUID.fromString(billingId)).get();
        model.addAttribute("billingInfo", billingInfo);
        return "user/payBilling";
    }

    @PostMapping("/profile/submitPayBilling")
    public String submitPayBilling(Model model, @RequestParam UUID billingId, @RequestParam String code16xCurrentClient) {
        Client client = getClient();
        model.addAttribute("bankAccounts", client.getBankAccounts());

        try {
            Billing billing = paymentService.payByBillingId(client.getId(), billingId, code16xCurrentClient);
            model.addAttribute("message", "Платіж успішно проведено");
            model.addAttribute("completePaymentLink", billing.getCompletePaymentLink());
        } catch (Exception e) {
            log.warn("Not complete billing payment", e);
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

    @GetMapping("/profile/requestRegisterBusinessAccount")
    public String registerBusinessAccount(Model model) {
        err(() -> clientService.addBusinessRole(getSec().getClient()), model, "Заявку оброблено");
        return "user/result";
    }

    @GetMapping("/profile/business/requestBankAccount")
    public String requestBusinessBankAccount(Model model) {
        err(() -> clientService.requestBusinessBankAccount(getSec().getClient()), model, "Заявку оброблено");
        return "user/result";
    }
    @GetMapping("/profile/business/registerBusinessApp")
    public String registerBusinessApp(Model model) {
        model.addAttribute("bankAccounts", getClient().getBankAccounts());
        return "user/business/registerApp";
    }

    @PostMapping("/profile/business/registerBusinessApp")
    public String registerBusinessApp(@ModelAttribute BusinessApp app, Model model) {
        err(() -> clientService.registerBusinessApp(getClient(), app), model, "Заявку оброблено");
        return "user/result";
    }
}
