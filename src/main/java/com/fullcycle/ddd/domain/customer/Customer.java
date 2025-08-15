package com.fullcycle.ddd.domain.customer;

import com.fullcycle.ddd.domain.common.Entity;
import com.fullcycle.ddd.domain.valueobject.Email;
import com.fullcycle.ddd.domain.valueobject.Name;

import java.util.Objects;

public class Customer extends Entity {

    private Name name;
    private Email email;

    public Customer(String id, Name name, Email email) {
        super(id);
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }

    // Getters
    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    // Exemplo de regra de negócio: atualizar email
    public void updateEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail, "Email cannot be null");
    }
}