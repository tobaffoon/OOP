package ru.nsu.amazyar.Operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Natural logarithm operation.
 */
public class Log extends Operation {

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

        return Math.log(arguments.get(0));
    }
}
