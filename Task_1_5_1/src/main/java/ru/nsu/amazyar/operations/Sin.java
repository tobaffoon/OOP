package ru.nsu.amazyar.operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Sines operation.
 */
public class Sin extends Operation {

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public double calculate(List<Double> arguments) {
        return Math.sin(arguments.get(0));
    }
}
