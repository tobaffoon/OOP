package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Evaluates an expression in prefix form.
 */
public class Calculator {

    private static final List<String> ALLOWED_OPERATIONS = OperationFactory.getAllowedOperations();
    private static final List<String> ALLOWED_CONSTANTS = ConstantsFactory.getAllowedConstants();

    /**
     * Evaluate expression in prefix form. Uses stack to evaluate
     *
     * @param input expression as string
     * @return result of evaluation
     */
    public static double evaluate(String input) {
        Stack<Double> evalStack = new Stack<>();
        List<String> tokens = tokenize(input);
        Collections.reverse(tokens);
        for (String token : tokens) {
            // token is operation
            if (ALLOWED_OPERATIONS.contains(token)) {
                List<Double> currentArguments = new ArrayList<>();
                Operation operation = OperationFactory.getOperation(token);
                try {
                    for (int i = 0; i < operation.getArity(); i++) {
                        currentArguments.add(evalStack.pop());
                    }

                    evalStack.push(operation.calculate(currentArguments));
                } catch (EmptyStackException e) {
                    throw new IllegalStateException("Wrong number of arguments");
                }
            } else if (ALLOWED_CONSTANTS.contains(token)) {       // token is a math constant
                evalStack.push(ConstantsFactory.getConstant(token));
            } else {          // token is an operand (number)
                try {
                    double number = Double.parseDouble(token);
                    evalStack.push(number);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Word is neither operation or a number");
                }
            }
        }

        // after evaluation of correct expression the only element on stack should be the result
        if (evalStack.size() != 1) {
            throw new IllegalStateException("Incorrect expression");
        } else {
            return evalStack.pop();
        }
    }

    private static List<String> tokenize(String string) {
        return new ArrayList<>(List.of(string.trim().toLowerCase().split(" ")));
    }
}
