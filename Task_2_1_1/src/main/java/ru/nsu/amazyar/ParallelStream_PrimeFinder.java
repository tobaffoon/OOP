package ru.nsu.amazyar;

import java.util.List;

/**
 * Checks list for primes via parallelStream.
 */
public class ParallelStream_PrimeFinder extends PrimeFinder {

    /**
     * Checks for primes in list.
     */
    public boolean containsNoPrimes(List<Integer> collection) {
        return collection.parallelStream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
