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
        Stack<Double> eval_stack = new Stack<>();
        List<String> tokens = tokenize(input);
        Collections.reverse(tokens);
        for (String token : tokens) {
            // token is operation
            if (ALLOWED_OPERATIONS.contains(token)) {
                List<Double> currentArguments = new ArrayList<>();
                Operation operation = OperationFactory.getOperation(token);
                try {
                    for (int i = 0; i < operation.getArity(); i++) {
                        currentArguments.add(eval_stack.pop());
                    }

                    eval_stack.push(operation.calculate(currentArguments));
                }catch (EmptyStackException e){
                    throw new IllegalStateException("Wrong number of arguments");
                }
            }
            // token is a math constant
            else if (ALLOWED_CONSTANTS.contains(token)) {
                eval_stack.push(ConstantsFactory.getConstant(token));
            }
            // token is an operand (number)
            else {
                try {
                    double number = Double.parseDouble(token);
                    eval_stack.push(number);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Word is neither operation or a number");
                }
            }
        }

        // after evaluation of correct expression the only element on stack should be the result
        if (eval_stack.size() != 1) {
            throw new IllegalStateException("Incorrect expression");
        } else {
            return eval_stack.pop();
        }
    }

    private static List<String> tokenize(String string) {
        return new ArrayList<>(List.of(string.trim().toLowerCase().split(" ")));
    }
}
