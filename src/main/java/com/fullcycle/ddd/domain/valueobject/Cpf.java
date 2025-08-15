package com.fullcycle.ddd.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Cpf {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    private final String value;

    public Cpf(String value) {
        Objects.requireNonNull(value, "CPF cannot be null");
        String sanitized = value.replaceAll("\\D", ""); // remove caracteres não numéricos

        if (!CPF_PATTERN.matcher(sanitized).matches()) {
            throw new IllegalArgumentException("Invalid CPF format");
        }

        // Aqui você pode adicionar validação de dígito verificador se desejar
        this.value = sanitized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cpf)) return false;
        Cpf other = (Cpf) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}