package ru.nsu.amazyar;

import java.lang.Math;
import java.util.List;

/**
 * Finds primes presence in Integer list.
 */
public class PrimeFinder {

    /**
     * Check number for being prime.
     */
    public static boolean simplePrimeCheck(Integer number) {
        if (number == null) {
            throw new NullPointerException();
        }

        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }

        int factorUpperBound = (int) Math.sqrt(number);
        for (int i = 3; i <= factorUpperBound; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check list for containing primes.
     */
    public boolean containsNoPrimes(List<Integer> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        return collection.stream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
