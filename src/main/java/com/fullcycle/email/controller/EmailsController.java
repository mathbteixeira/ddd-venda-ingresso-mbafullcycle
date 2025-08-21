package com.fullcycle.email.controller;

import com.fullcycle.email.service.EmailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailsController {

    private final EmailsService emailsService;

    public EmailsController(EmailsService emailsService) {
        this.emailsService = emailsService;
    }

    @GetMapping("/")
    public String getHello() {
        return emailsService.getHello();
    }
}