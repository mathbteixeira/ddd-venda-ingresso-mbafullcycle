package com.fullcycle.domain;

import java.util.Objects;

public abstract class Entity<ID> {

    protected final ID id;

    protected Entity(ID id) {
        this.id = id;
    }

    /** usado apenas por Hibernate/JPA reflection */
    protected Entity() {
        this.id = null;
    }

    public ID getId() {
        return id;
    }

    /**
     * Para casos onde o id é um ValueObject como Uuid, retornar seu valor primitivo
     */
    public Object getIdValue() {
        return id != null ? id.toString() : null;
    }

    /**
     * Cada entity sabe se transformar em um objeto serializável (JSON friendly)
     */
    public abstract Object toJson();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Entity<?> other = (Entity<?>) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}