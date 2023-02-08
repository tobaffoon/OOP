package ru.nsu.amazyar;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numbers = scanner.nextLine();
        scanner.close();
        List<Integer> array = Arrays.stream(numbers.split(" +"))
            .map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(PrimeFinder.containsNoPrimes(array));
    }
}
