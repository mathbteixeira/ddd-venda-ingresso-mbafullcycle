package com.fullcycle.ddd.domain.ingresso;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Value Object representing the price of a ticket.
 * In DDD, value objects are immutable and represent concepts that don't have identity.
 */
@Getter
@EqualsAndHashCode
public class PrecoIngresso {
    private final BigDecimal valor;

    private PrecoIngresso(BigDecimal valor) {
        this.valor = valor;
    }

    public static PrecoIngresso of(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Preço não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        return new PrecoIngresso(valor);
    }

    public static PrecoIngresso zero() {
        return new PrecoIngresso(BigDecimal.ZERO);
    }

    public PrecoIngresso adicionar(PrecoIngresso outro) {
        return new PrecoIngresso(this.valor.add(outro.valor));
    }

    public PrecoIngresso subtrair(PrecoIngresso outro) {
        BigDecimal resultado = this.valor.subtract(outro.valor);
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resultado da subtração não pode ser negativo");
        }
        return new PrecoIngresso(resultado);
    }

    public PrecoIngresso aplicarDesconto(BigDecimal percentual) {
        if (percentual.compareTo(BigDecimal.ZERO) < 0 || percentual.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
        }
        
        BigDecimal fator = BigDecimal.ONE.subtract(percentual.divide(new BigDecimal("100")));
        return new PrecoIngresso(this.valor.multiply(fator).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}