package com.fullcycle.domain.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void deveCriarUmNomeValido() {
        Name name = new Name("aaaaaa");
        assertEquals("aaaaaa", name.getValue());
    }
}