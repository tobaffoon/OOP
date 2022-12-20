package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantsFactory {
    public static final Map<String, Double> CONSTANTS_FACTORY;

    static {
        CONSTANTS_FACTORY = new HashMap<>();

        CONSTANTS_FACTORY.put("e", Math.E);
        CONSTANTS_FACTORY.put("-e", -Math.E);
        CONSTANTS_FACTORY.put("pi", Math.PI);
        CONSTANTS_FACTORY.put("-pi", -Math.PI);
    }

    public static List<String> getAllowedConstants(){
        return new ArrayList<>(CONSTANTS_FACTORY.keySet());
    }

    public static Double getConstant(String constant) {
        return CONSTANTS_FACTORY.get(constant);
    }
}
