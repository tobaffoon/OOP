package ru.nsu.amazyar;

import java.util.List;

/**
 * N-ary operation on Doubles.
 */
public abstract class Operation {

    /**
     * Get arity of the operation.
     */
    public abstract int getArity();

    /**
     * Evaluate operation with given operands.
     * Throws respected errors if evaluation couldn't complete
     * @param arguments list of operands
     * @return result of evaluation
     */
    public abstract double calculate(List<Double> arguments);
}
