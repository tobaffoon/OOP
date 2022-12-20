package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    @Test
    public void referenceTest(){
        Assertions.assertEquals(0.0, Calculator.evaluate("sin + - 1 2 1"));
    }
}