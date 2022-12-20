package ru.nsu.amazyar.Operations;

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
        // check for correct number of arguments
        if (arguments.size() != getArity()) {
            throw new IllegalStateException("Wrong number of arguments");
        }

        return arguments.get(0) - arguments.get(1);
    }
}
