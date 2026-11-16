package com.devarsh.careeros.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/ping")
    public String adminPing(Authentication authentication) {
        return "Hello admin " + authentication.getName() + ", you have admin access!";
    }
}