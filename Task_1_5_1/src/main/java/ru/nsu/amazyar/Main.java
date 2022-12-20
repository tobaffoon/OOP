package ru.nsu.amazyar;

public class Main {
    public static void main(String[] args) {
        String expression = TerminalReader.readLine();

        System.out.println(Calculator.evaluate(expression));
    }
}
