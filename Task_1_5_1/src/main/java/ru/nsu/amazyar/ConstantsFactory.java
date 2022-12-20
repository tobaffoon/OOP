package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory of math constants from string.
 */
public class ConstantsFactory {
    public static final Map<String, Double> CONSTANTS_FACTORY;

    static {
        CONSTANTS_FACTORY = new HashMap<>();

        CONSTANTS_FACTORY.put("e", Math.E);
        CONSTANTS_FACTORY.put("-e", -Math.E);
        CONSTANTS_FACTORY.put("pi", Math.PI);
        CONSTANTS_FACTORY.put("-pi", -Math.PI);
    }

    /**
     * Get supported constants.
     * @return list of constants as strings
     */
    public static List<String> getAllowedConstants(){
        return new ArrayList<>(CONSTANTS_FACTORY.keySet());
    }

    /**
     * Create a constant with factory.
     * @param constant String representation of a constant
     * @return constant as double
     */
    public static Double getConstant(String constant) {
        return CONSTANTS_FACTORY.get(constant);
    }
}
