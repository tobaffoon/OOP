package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import ru.nsu.amazyar.Operations.OperationFactory;

public class Calculator {
    private static final Stack<Double> eval_stack = new Stack<>();
    private static final List<String> ALLOWED_OPERATIONS = OperationFactory.getAllowedOperations();

    public static double evaluate(String input){
        List<String> tokens = tokenize(input);
        Collections.reverse(tokens);
        for(String token : tokens){
            if(ALLOWED_OPERATIONS.contains(token)){
                List<Double> currentArguments = new ArrayList<>();
                Operation operation = OperationFactory.getOperation(token);
                for (int i = 0; i < operation.getArity(); i++) {
                    currentArguments.add(eval_stack.pop()); //first we reverse, then put on stack
                }                                          //so eventually args are in correct order

                eval_stack.push(operation.calculate(currentArguments));
            }

            else{
                try{
                    double number = Double.parseDouble(token);
                    eval_stack.push(number);
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Word is neither operation or a number");
                }
            }
        }

        if (eval_stack.size() != 1) {
            throw new IllegalStateException("Incorrect order");
        } else {
            return eval_stack.pop();
        }
    }

    private static List<String> tokenize(String string) {
        return new ArrayList<>(List.of(string.split(" ")));
    }
}
