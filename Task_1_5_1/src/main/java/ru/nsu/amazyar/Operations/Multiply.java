package ru.nsu.amazyar.Operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

/**
 * Multiplication operation.
 */
public class Multiply extends Operation {

    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public double calculate(List<Double> arguments) {
        return arguments.get(0) * arguments.get(1);
    }
}
