package ru.nsu.amazyar;

import java.util.List;

/**
 * Checks list for primes via parallelStream.
 */
public class ParallelStreamPrimeFinder extends PrimeFinder {

    /**
     * Checks for primes in list.
     */
    public boolean containsNoPrimes(List<Integer> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        return collection.parallelStream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
