package com.impllife.bankmock.controller;

import com.impllife.bankmock.data.entity.Client;
import com.impllife.bankmock.data.entity.security.ClientSec;
import com.impllife.bankmock.data.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.UUID;

public abstract class BaseMvcController {

    @Autowired
    private ClientRepo clientRepo;

    public ClientSec getSec() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ClientSec) authentication.getPrincipal();
    }
    public Client getClient() {
//        log.info("id1 {}, id2 {}", clientRepo.findAll().get(0).getId(), getSec().getClient().getId());
//        log.info("id1 e id2 {}", Objects.equals(clientRepo.findAll().get(0).getId(), getSec().getClient().getId()));
        UUID id = getSec().getClient().getId();
        return clientRepo.findById(id).get();
    }
    public void doWithErrHandle(Runnable run, Model model, String message) {
        try {
            run.run();
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }
    }
    public void doWithErrHandle(Runnable action, Model model, Runnable reactionOnErr) {

    }
}
