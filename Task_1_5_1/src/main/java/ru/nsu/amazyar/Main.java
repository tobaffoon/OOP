package ru.nsu.amazyar;

/**
 * Main class calling TerminalReader and Calculator.
 */
public class Main {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        String expression = TerminalReader.readLine();

        System.out.println(Calculator.evaluate(expression));
    }
}
