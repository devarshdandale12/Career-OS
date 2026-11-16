package com.devarsh.careeros.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/secure-ping")
    public String securePing(Authentication authentication) {
        return "Hello " + authentication.getName() + ", your token works!";
    }
}
