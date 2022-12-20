package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.amazyar.Operations.Add;
import ru.nsu.amazyar.Operations.Cos;
import ru.nsu.amazyar.Operations.Divide;
import ru.nsu.amazyar.Operations.Log;
import ru.nsu.amazyar.Operations.Multiply;
import ru.nsu.amazyar.Operations.Pow;
import ru.nsu.amazyar.Operations.Sin;
import ru.nsu.amazyar.Operations.Subtract;

public class OperationFactory {
    public static final Map<String, Operation> OPERATION_FACTORY;

    static {
        OPERATION_FACTORY = new HashMap<>();

        OPERATION_FACTORY.put("+", new Add());
        OPERATION_FACTORY.put("-", new Subtract());
        OPERATION_FACTORY.put("*", new Multiply());
        OPERATION_FACTORY.put("/", new Divide());
        OPERATION_FACTORY.put("pow", new Pow());
        OPERATION_FACTORY.put("log", new Log());
        OPERATION_FACTORY.put("sin", new Sin());
        OPERATION_FACTORY.put("cos", new Cos());
    }

    public static List<String> getAllowedOperations(){
        return new ArrayList<>(OPERATION_FACTORY.keySet());
    }

    public static Operation getOperation(String operator) {
        return OPERATION_FACTORY.get(operator);
    }
}
