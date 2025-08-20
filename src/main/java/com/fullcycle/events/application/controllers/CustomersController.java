package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.services.CustomerService;
import com.fullcycle.events.domain.entities.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> list() throws Exception {
        return customerService.list();
    }

    @PostMapping
    public Customer create(@RequestBody CustomerCreateRequest body) throws Exception {
        return customerService.register(body.getName(), body.getCpf());
    }

    // DTO para receber o corpo da requisição
    public static class CustomerCreateRequest {
        private String name;
        private String cpf;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
    }
}