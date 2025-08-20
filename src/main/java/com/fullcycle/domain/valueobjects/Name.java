package com.fullcycle.domain.valueobjects;

public class Name extends ValueObject<String> {

    public Name(String name) {
        super(name);
        if (!isValid()) {
            throw new IllegalArgumentException("Name must have at least 3 characters");
        }
    }

    public boolean isValid() {
        return this.value != null && this.value.length() >= 3;
    }
}