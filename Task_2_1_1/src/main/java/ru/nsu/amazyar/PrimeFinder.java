package ru.nsu.amazyar;

import java.util.Collection;
import java.lang.Math;

public interface PrimeFinder {
    private static boolean simplePrimeCheck(Integer number) {
        if(number <= 0){
            return false;
        }
        if(number % 2 == 0){
            return true;
        }

        int factorUpperBound = (int) Math.sqrt(number);
        for(int i = 3; i <= factorUpperBound; i+=2){
            if(number % i == 0) return true;
        }

        return false;
    }

    static boolean containsNoPrimes(Collection<Integer> collection){
        return collection.stream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
