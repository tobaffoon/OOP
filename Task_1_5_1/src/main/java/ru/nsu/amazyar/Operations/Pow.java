package ru.nsu.amazyar.Operations;

import java.util.List;
import ru.nsu.amazyar.Operation;

public class Pow extends Operation {
    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public double calculate(List<Double> arguments) {
        if (arguments.size() != getArity()) {
            throw new IllegalStateException("Wrong number of arguments");
        }

        return Math.pow(arguments.get(0), arguments.get(1));
    }
}