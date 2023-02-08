package ru.nsu.amazyar;

import java.util.List;

public class ParallelStream_PrimeFinder extends PrimeFinder{
    public boolean containsNoPrimes(List<Integer> collection){
        return collection.parallelStream().noneMatch(PrimeFinder::simplePrimeCheck);
    }
}
