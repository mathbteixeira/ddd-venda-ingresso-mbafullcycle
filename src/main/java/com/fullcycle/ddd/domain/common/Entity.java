package com.fullcycle.ddd.domain.common;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

    private final String id;

    protected Entity(String id) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity other = (Entity) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}