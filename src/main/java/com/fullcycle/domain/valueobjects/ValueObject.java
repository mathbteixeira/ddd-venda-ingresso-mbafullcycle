package com.fullcycle.domain.valueobjects;

import java.util.Objects;

/**
 * Classe base para todos os Value Objects.
 * @param <T> tipo do valor encapsulado.
 */
public abstract class ValueObject<T> {

    protected final T value;

    protected ValueObject(T value) {
        this.value = deepFreeze(value);
    }

    /**
     * Retorna o valor do Value Object.
     */
    public T getValue() {
        return value;
    }

    /**
     * Compara este ValueObject com outro para igualdade profunda.
     */
    @SuppressWarnings("unchecked")
    public boolean equals(ValueObject<T> obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return Objects.deepEquals(this.value, obj.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        if (value == null) return "null";
        return value.toString();
    }

    /**
     * "Congela" o objeto, garantindo imutabilidade.
     * Em Java, usamos objetos imutáveis ou não expomos setters.
     */
    protected T deepFreeze(T obj) {
        // Java não possui equivalente direto a Object.freeze.
        // Assume-se que o objeto seja imutável ou tratado com imutabilidade.
        return obj;
    }
}