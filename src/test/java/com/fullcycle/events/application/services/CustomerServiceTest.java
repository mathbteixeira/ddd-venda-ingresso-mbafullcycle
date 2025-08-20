package com.fullcycle.events.application.services;

import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.repositories.ICustomerRepository;
import com.fullcycle.infrastructure.uow.UnitOfWorkJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CustomerService.class, UnitOfWorkJpa.class})
class CustomerServiceTest {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private UnitOfWorkJpa unitOfWork;

    private CustomerService customerService;

    @BeforeEach
    void setup() {
        customerService = new CustomerService(customerRepository, unitOfWork);
    }

    @Test
    void deveListarOsCustomers() {
        Customer customer = Customer.create("Customer 1", "70375887091");
        customerRepository.add(customer);
        unitOfWork.commit();

        List<Customer> customers = customerService.list();
        assertFalse(customers.isEmpty());
        System.out.println(customers);
    }

    @Test
    void deveRegistrarUmCustomer() {
        Customer customer = customerService.register("Customer 1", "70375887091");

        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("Customer 1", customer.getName());
        assertEquals("70375887091", customer.getCpf().getValue());

        Customer customerFound = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        assertNotNull(customerFound);
        assertEquals(customer.getId(), customerFound.getId());
        assertEquals("Customer 1", customerFound.getName());
        assertEquals("70375887091", customerFound.getCpf().getValue());
    }
}