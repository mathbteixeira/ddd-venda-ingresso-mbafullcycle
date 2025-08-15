package com.fullcycle.ddd.domain.valueobject;

import java.util.Objects;

public abstract class ValueObject<T> {

    protected abstract T getValue();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ValueObject<?>)) return false;
        ValueObject<?> other = (ValueObject<?>) obj;
        return Objects.equals(this.getValue(), other.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}