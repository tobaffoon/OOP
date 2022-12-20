package ru.nsu.amazyar.operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Square root operation.
 */
public class SquareRoot extends Operation {

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public double calculate(List<Double> arguments) {
        return Math.sqrt(arguments.get(0));
    }
}
