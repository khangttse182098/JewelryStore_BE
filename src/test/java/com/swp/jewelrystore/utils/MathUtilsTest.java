package com.swp.jewelrystore.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void getFactorial() {
        assertEquals(24, MathUtils.getFactorial(4));
        assertEquals(120, MathUtils.getFactorial(5));
        assertEquals(720, MathUtils.getFactorial(6));
    }

}