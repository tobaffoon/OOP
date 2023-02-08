package ru.nsu.amazyar;

import java.lang.Math;
import java.util.List;

public class PrimeFinder {
    private static boolean simplePrimeCheck(Integer number) {
        if(number <= 1){
            return false;
        }
        if(number == 2){
            return true;
        }
        if(number % 2 == 0){
            return false;
        }

        int factorUpperBound = (int) Math.sqrt(number);
        for(int i = 3; i <= factorUpperBound; i+=2){
            if(number % i == 0) return false;
        }

        return true;
    }

    public boolean containsNoPrimes(List<Integer> collection){
        return collection.stream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
