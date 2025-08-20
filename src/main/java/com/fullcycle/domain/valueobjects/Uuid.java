package com.fullcycle.domain.valueobjects;

import java.util.UUID;

public class Uuid extends ValueObject<String> {

    public Uuid() {
        super(UUID.randomUUID().toString());
        validate();
    }

    public Uuid(String id) {
        super(id != null ? id : UUID.randomUUID().toString());
        validate();
    }

    private void validate() {
        try {
            UUID.fromString(this.value);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidException(this.value);
        }
    }
}