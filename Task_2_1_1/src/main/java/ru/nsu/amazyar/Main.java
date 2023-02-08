package ru.nsu.amazyar;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numbers = scanner.nextLine();
        scanner.close();
        List<Integer> array = Arrays.stream(numbers.split(" +"))
            .map(Integer::parseInt).collect(Collectors.toList());
        MultithreadingPrimeFinder primeFinder = new MultithreadingPrimeFinder();
        System.out.println(primeFinder.containsNoPrimes(array, 2));
    }
}
