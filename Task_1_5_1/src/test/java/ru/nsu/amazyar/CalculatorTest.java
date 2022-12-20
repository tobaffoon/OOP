package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    final double accuracy = 0.000000001;

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
        double cosPi = Calculator.evaluate("cos pi");
        Assertions.assertTrue(-1 - accuracy < cosPi && cosPi < -1 + accuracy);

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
        double sinPi2 = Calculator.evaluate("sin / pi 2");
        Assertions.assertTrue(1 - accuracy < sinPi2 && sinPi2 < 1 + accuracy);

        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sin pi pi"));
        Assertions.assertThrows(IllegalStateException.class,
            () -> Calculator.evaluate("sin"));
    }

    @Test
    public void sqrtTest() {
        Assertions.assertEquals(10,
            Calculator.evaluate("sqrt 100"));
        double sinPi4 = Calculator.evaluate("sin / pi 4");
        double sqrt2Div2 = Calculator.evaluate("/ sqrt 2 2");
        Assertions.assertTrue(sinPi4 - accuracy < sqrt2Div2
            && sqrt2Div2 < sinPi4 + accuracy);
        Assertions.assertEquals(Double.NaN, Calculator.evaluate("sqrt -1"));

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
    public void mathConstantsTest() {
        double eApproximation = Calculator.evaluate("pow + 1 / 1 1000000000 1000000000");
        Assertions.assertTrue(Math.E - 0.0001 < eApproximation
            && eApproximation < Math.E + 0.0001);

        double goldRatioApproximation = Calculator.evaluate("/ + 1 sqrt 5 2");
        Assertions.assertTrue(1.618033988 - accuracy < goldRatioApproximation
            && goldRatioApproximation < 1.618033988 + accuracy);
    }

    @Test
    public void realEvaluationTest() {
        double evaluatedValue = Calculator.evaluate("/ sqrt cos sin 0 log pow e 2");
        System.out.println(evaluatedValue);
        Assertions.assertTrue(0.5 - accuracy < evaluatedValue
            && evaluatedValue < 0.5 + accuracy);
    }
}