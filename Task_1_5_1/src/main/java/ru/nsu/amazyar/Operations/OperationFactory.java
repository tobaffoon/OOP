package ru.nsu.amazyar.Operations;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.amazyar.Operation;

public class OperationFactory {
    public static final Map<String, Operation> OPERATION_FACTORY;

    static {
        OPERATION_FACTORY = new HashMap<>();

        OPERATION_FACTORY.put("+", new Add());
    }
    public static Operation getOperation(String operator) {
        if (OPERATION_FACTORY.containsKey(operator)) {
            return OPERATION_FACTORY.get(operator);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
