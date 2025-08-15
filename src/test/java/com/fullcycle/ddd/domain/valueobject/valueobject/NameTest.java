package com.fullcycle.ddd.domain.valueobject.valueobject;

import com.fullcycle.ddd.domain.valueobject.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void shouldCreateNameWhenValid() {
        Name name = new Name("Matheus Batista");
        assertEquals("Matheus Batista", name.getValue());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Name(""));
    }

    @Test
    void shouldTrimName() {
        Name name = new Name("  John Doe  ");
        assertEquals("John Doe", name.getValue());
    }
}