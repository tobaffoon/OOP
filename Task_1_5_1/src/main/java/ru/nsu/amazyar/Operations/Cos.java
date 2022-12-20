package ru.nsu.amazyar.Operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Cosines operation.
 */
public class Cos extends Operation {
    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public double calculate(List<Double> arguments) {
        // check for correct number of arguments
        if (arguments.size() != getArity()) {
            throw new IllegalStateException("Wrong number of arguments");
        }

        return Math.cos(arguments.get(0));
    }
}