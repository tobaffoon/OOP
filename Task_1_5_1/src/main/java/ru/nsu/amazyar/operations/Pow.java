package ru.nsu.amazyar.operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Exponentiation operation.
 */
public class Pow extends Operation {

    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public double calculate(List<Double> arguments) {
        return Math.pow(arguments.get(0), arguments.get(1));
    }
}
