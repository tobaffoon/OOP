package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import ru.nsu.amazyar.Operations.OperationFactory;

public class Calculator {
    private static final Stack<Double> arguments = new Stack<>();
    private static final List<String> ALLOWED_OPERATIONS = OperationFactory.getAllowedOperations();

    public static double evaluate(String input){
        List<String> tokens = tokenize(input);
        Collections.reverse(tokens);
        for(String token : tokens){
            if(ALLOWED_OPERATIONS.contains(token)){
                List<Double> currentArguments = new ArrayList<>();
                Operation operation = OperationFactory.getOperation(token);
                for (int i = 0; i < operation.getArity(); i++) {
                    currentArguments.add(arguments.pop()); //first we reverse, then put on stack
                }                                          //so eventually args are in correct order

                arguments.push(operation.calculate(currentArguments));
            }

            else{
                try{
                    double number = Double.parseDouble(token);
                    arguments.push(number);
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Word is neither operation or a number");
                }
            }
        }

        if (arguments.size() != 1) {
            throw new IllegalStateException("Incorrect order");
        } else {
            return arguments.pop();
        }
    }

    private static List<String> tokenize(String string) {
        return new ArrayList<>(List.of(string.split(" ")));
    }
}
