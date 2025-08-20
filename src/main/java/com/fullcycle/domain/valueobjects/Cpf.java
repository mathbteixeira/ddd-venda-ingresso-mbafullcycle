package com.fullcycle.domain.valueobjects;

import java.util.regex.Pattern;

public class Cpf extends ValueObject<String> {

    public Cpf(String value) {
        super(value.replaceAll("\\D", ""));
        validate();
    }

    private void validate() {
        if (this.value.length() != 11) {
            throw new InvalidCpfException(
                    "CPF must have 11 digits, but has " + this.value.length() + " digits"
            );
        }

        // Verifica se todos os dígitos são iguais
        if (Pattern.matches("(\\d)\\1{10}", this.value)) {
            throw new InvalidCpfException("CPF must have at least two different digits");
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(this.value.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) firstDigit = 0;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(this.value.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) secondDigit = 0;

        if (firstDigit != Character.getNumericValue(this.value.charAt(9)) ||
                secondDigit != Character.getNumericValue(this.value.charAt(10))) {
            throw new InvalidCpfException("CPF is invalid");
        }
    }

    @Override
    public String getValue() {
        return "";
    }
}