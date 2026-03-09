package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void greeterProducesExpectedMessage() {
        Greeter g = new Greeter("Hi");
        assertEquals("Hi, Ada!", g.greet("Ada"));
    }

    Calculator calc = new Calculator();

    @Test
    void testSum() {
        assertEquals(8, calc.sum(3, 5));
    }

    @Test
    void testDiff() {
        assertEquals(2, calc.diff(7, 5));
    }

    @Test
    void testProduct() {
        assertEquals(15, calc.product(3, 5));
    }

    @Test
    void testAverage() {
        assertEquals(4.0, calc.average(3, 5));
}