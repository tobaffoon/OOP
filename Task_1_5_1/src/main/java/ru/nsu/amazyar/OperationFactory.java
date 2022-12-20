package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import ru.nsu.amazyar.Operation;
import ru.nsu.amazyar.Operations.Add;

public class OperationFactory {
    public static final Map<String, Operation> OPERATION_FACTORY;

    static {
        OPERATION_FACTORY = new HashMap<>();

        OPERATION_FACTORY.put("+", new Add());
    }

    public static List<String> getAllowedOperations(){
        return new ArrayList<>(OPERATION_FACTORY.keySet());
    }

    public static Operation getOperation(String operator) {
        if (OPERATION_FACTORY.containsKey(operator)) {
            return OPERATION_FACTORY.get(operator);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
