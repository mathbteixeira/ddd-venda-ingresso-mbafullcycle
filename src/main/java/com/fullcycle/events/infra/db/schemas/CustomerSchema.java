package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.domain.valueobjects.Cpf;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.Customer.CustomerId;
import com.fullcycle.events.infra.db.types.CustomerIdSchemaType;
import com.fullcycle.events.infra.db.types.CpfSchemaType;
import jakarta.persistence.*;

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf"}))
public class CustomerSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = CustomerIdSchemaType.class)
    private CustomerId id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @Convert(converter = CpfSchemaType.class)
    private Cpf cpf;

    protected CustomerSchema() {} // JPA

    public CustomerSchema(CustomerId id, String name, Cpf cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    public CustomerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Customer toDomain() {
        return new Customer(id, cpf, name);
    }
}