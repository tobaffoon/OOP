package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    double accuracy = 0.000000001;

    @Test
    public void referenceTest() {
        Assertions.assertEquals(0.0, Calculator.evaluate("sin + - 1 2 1"));
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(1.8,
            Calculator.evaluate(" + + + + 1 0.5 0.25 0.125 -0.075 "));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("+ 1 2 3"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("+ 1 2 + 3"));
    }

    @Test
    public void cosTest() {
        Assertions.assertEquals(1,
            Calculator.evaluate("cos 0"));
        double cos_pi = Calculator.evaluate("cos pi");
        Assertions.assertTrue(-1 - accuracy < cos_pi && cos_pi < -1 + accuracy);

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("cos 1 2"));
    }

    @Test
    public void divTest() {
        Assertions.assertEquals(0.05,
            Calculator.evaluate("/ 1 20"));
        Assertions.assertEquals(Double.POSITIVE_INFINITY,
            Calculator.evaluate("/ 100 0"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("/ 1 2 3"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("/ 1"));
    }

    @Test
    public void logTest() {
        Assertions.assertEquals(1,
            Calculator.evaluate("log e"));
        Assertions.assertEquals(2,
            Calculator.evaluate("/ log 100 log 10"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("log 1 2"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("log"));
    }

    @Test
    public void multTest() {
        Assertions.assertEquals(8,
            Calculator.evaluate("* * 2 2 2"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("* 1 2 * 3"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("* 1 2 3"));
    }

    @Test
    public void powTest() {
        Assertions.assertEquals(8,
            Calculator.evaluate("pow 2 3"));
        Assertions.assertEquals(1,
            Calculator.evaluate("pow 0 0"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("pow 2"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("pow 1 1 1"));
    }

    @Test
    public void sinTest() {
        Assertions.assertEquals(0,
            Calculator.evaluate("sin 0"));
        double sin_pi_2 = Calculator.evaluate("sin / pi 2");
        Assertions.assertTrue(1 - accuracy < sin_pi_2 && sin_pi_2 < 1 + accuracy);

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sin pi pi"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sin"));
    }

    @Test
    public void sqrtTest() {
        Assertions.assertEquals(10,
            Calculator.evaluate("sqrt 100"));
        double sin_pi_4 = Calculator.evaluate("sin / pi 4");
        double sqrt_2_div_2 = Calculator.evaluate("/ sqrt 2 2");
        Assertions.assertTrue(sin_pi_4 - accuracy < sqrt_2_div_2
            && sqrt_2_div_2 < sin_pi_4 + accuracy);

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sqrt 1 2"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sqrt"));
    }

    @Test
    public void subTest() {
        Assertions.assertEquals(12.5,
            Calculator.evaluate("- - - 100 50 25 12.5"));
        Assertions.assertEquals(1600,
            Calculator.evaluate("- 800 -800"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("- 1 2 - 3"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("- 1 2 3"));
    }

    @Test
    public void mathConstantsTest(){
        double e_approximation = Calculator.evaluate("pow + 1 / 1 1000000000 1000000000");
        Assertions.assertTrue(Math.E - 0.0001 < e_approximation
            && e_approximation < Math.E + 0.0001);

        double gold_ratio_approximation = Calculator.evaluate("/ + 1 sqrt 5 2");
        System.out.println(gold_ratio_approximation);
        Assertions.assertTrue(1.618033988 - accuracy < gold_ratio_approximation
            && gold_ratio_approximation < 1.618033988 + accuracy);
    }
}