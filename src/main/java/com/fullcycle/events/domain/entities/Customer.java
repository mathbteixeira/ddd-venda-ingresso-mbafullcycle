package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.valueobjects.Cpf;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.events.CustomerChangedName;
import com.fullcycle.events.domain.events.CustomerCreated;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Customer extends AggregateRoot {

    public static class CustomerId extends Uuid {
        public CustomerId() {
            super();
        }

        public CustomerId(String value) {
            super(value);
        }
    }

    private CustomerId id;
    private Cpf cpf;
    private String name;

    // Construtor privado para criar via factory
    public Customer(CustomerId id, Cpf cpf, String name) {
        this.id = id != null ? id : new CustomerId();
        this.cpf = cpf;
        this.name = name;
    }

    public static Customer create(String name, String cpfValue) {
        Cpf cpf = new Cpf(cpfValue);
        Customer customer = new Customer(null, cpf, name);
        customer.addEvent(new CustomerCreated(customer.getId(), customer.getName(), customer.getCpf()));
        return customer;
    }

    public void changeName(String name) {
        this.name = name;
        this.addEvent(new CustomerChangedName(this.id, this.name));
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id.getValue());
        json.put("cpf", cpf.getValue());
        json.put("name", name);
        return json;
    }
}
