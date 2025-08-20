package com.fullcycle.events.application.services;

import com.fullcycle.application.IUnitOfWork;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.repositories.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final ICustomerRepository customerRepo;
    private final IUnitOfWork uow;

    public CustomerService(ICustomerRepository customerRepo, IUnitOfWork uow) {
        this.customerRepo = customerRepo;
        this.uow = uow;
    }

    public List<Customer> list() throws Exception {
        return customerRepo.findAll();
    }

    public Customer register(String name, String cpf) throws Exception {
        Customer customer = Customer.create(name, cpf);
        customerRepo.add(customer);
        uow.commit();
        return customer;
    }

    public Customer update(String id, String name) throws Exception {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (name != null) {
            customer.changeName(name);
        }
        customerRepo.add(customer);
        uow.commit();
        return customer;
    }
}