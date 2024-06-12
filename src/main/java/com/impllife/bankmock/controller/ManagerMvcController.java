package com.impllife.bankmock.controller;

import com.impllife.bankmock.services.interfaces.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Slf4j
@Controller
public class ManagerMvcController extends BaseMvcController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/manager/processingOrder")
    public String getListOrders(@RequestParam String id, @RequestParam String act, Model model) {
        err(() -> clientService.processingCreateOrder(UUID.fromString(id), act), model, "Заявку оброблено");
        return "user/result";
    }
}
