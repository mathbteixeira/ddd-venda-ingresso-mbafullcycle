package com.fullcycle.ddd.domain.customer;

import com.fullcycle.ddd.domain.valueobject.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithValidAttributes() {
        Email email = new Email("cliente@example.com");
        Customer customer = new Customer("c1", "Matheus Batista", email);

        assertEquals("Matheus Batista", customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals("c1", customer.getId());
    }

    @Test
    void shouldUpdateEmail() {
        Customer customer = new Customer("c1", "Matheus Batista", new Email("old@example.com"));
        Email newEmail = new Email("new@example.com");

        customer.updateEmail(newEmail);

        assertEquals(newEmail, customer.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Email email = new Email("cliente@example.com");
        assertThrows(NullPointerException.class, () -> new Customer("c1", null, email));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(NullPointerException.class, () -> new Customer("c1", "Matheus", null));
    }
}