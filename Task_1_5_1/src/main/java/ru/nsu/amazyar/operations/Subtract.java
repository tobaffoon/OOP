package ru.nsu.amazyar.operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Subtraction operation.
 */
public class Subtract extends Operation {

    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public double calculate(List<Double> arguments) {
        return arguments.get(0) - arguments.get(1);
    }
}
