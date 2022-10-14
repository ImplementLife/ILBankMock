package com.implementLife.BankMock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
    @GetMapping("/")
    public String main() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "user/profile";
    }

    @GetMapping("/profile")
    public String profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "user/profile";
    }
}
