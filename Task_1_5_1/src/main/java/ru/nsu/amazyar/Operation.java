package ru.nsu.amazyar;

import java.util.List;

public abstract class Operation {
    public abstract int getArity();
    public abstract double calculate(List<Double> arguments);
}
