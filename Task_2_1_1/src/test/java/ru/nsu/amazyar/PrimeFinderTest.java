package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PrimeFinderTest {

    static PrimeFinder primeFinder = new PrimeFinder();
    static MultithreadingPrimeFinder multithreadingPrimeFinder = new MultithreadingPrimeFinder();
    static ParallelStreamPrimeFinder parallelStreamPrimeFinder = new ParallelStreamPrimeFinder();
    static List<Integer> emptyList;
    static List<Integer> singlePrimeList;
    static List<Integer> singleNonPrimeList;
    static List<Integer> endingPrimeList;
    static List<Integer> bigNonPrimesList;
    static List<Integer> longNonPrimesList;

    @BeforeAll
    public static void listsInit() {
        emptyList = new ArrayList<>();

        singlePrimeList = new ArrayList<>(List.of(17));

        singleNonPrimeList = new ArrayList<>(39);

        endingPrimeList = new ArrayList<>();
        for (int i = 4; i <= 1024; i += 2) {
            endingPrimeList.add(i);
        }
        endingPrimeList.add(1_000_000_007);

        bigNonPrimesList = new ArrayList<>();
        for (int i = 1235; i <= 45340; i += 501) {
            bigNonPrimesList.add(i * (i + 2));
        }

        longNonPrimesList = new ArrayList<>();
        for (int i = 3; i <= 45340; i+=2) {
            for (int j = 2; j <= 1024; j+=2) {
                longNonPrimesList.add(i * (i+j));
            }
        }
    }

    @Test
    public void primeCheckerTest() {
        Assertions.assertTrue(PrimeFinder.simplePrimeCheck(2));
        Assertions.assertFalse(PrimeFinder.simplePrimeCheck(0));
        Assertions.assertFalse(PrimeFinder.simplePrimeCheck(1));
        Assertions.assertFalse(
            PrimeFinder.simplePrimeCheck(2 * 3 * 5 * 7 * 11 * 13 * 17 * 23 * 29));
    }

    @Test
    public void sequentialListCheckerTest() {
        Assertions.assertTrue(primeFinder.containsNoPrimes(emptyList));
        Assertions.assertFalse(primeFinder.containsNoPrimes(singlePrimeList));
        Assertions.assertTrue(primeFinder.containsNoPrimes(singleNonPrimeList));
        Assertions.assertFalse(primeFinder.containsNoPrimes(endingPrimeList));
        Assertions.assertTrue(primeFinder.containsNoPrimes(bigNonPrimesList));
        Assertions.assertTrue(primeFinder.containsNoPrimes(longNonPrimesList));
    }

    @Test
    public void multiThreadingListCheckerTest() {
        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(emptyList));
        Assertions.assertFalse(multithreadingPrimeFinder.containsNoPrimes(singlePrimeList));
        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(singleNonPrimeList));
        Assertions.assertFalse(multithreadingPrimeFinder.containsNoPrimes(endingPrimeList));
        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(bigNonPrimesList));
        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(longNonPrimesList));

        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(bigNonPrimesList, 1));
        Assertions.assertTrue(multithreadingPrimeFinder.containsNoPrimes(longNonPrimesList, 100));
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> multithreadingPrimeFinder.containsNoPrimes(bigNonPrimesList, 0));
    }

    @Test
    public void parallelStreamListCheckerTest() {
        Assertions.assertTrue(parallelStreamPrimeFinder.containsNoPrimes(emptyList));
        Assertions.assertFalse(parallelStreamPrimeFinder.containsNoPrimes(singlePrimeList));
        Assertions.assertTrue(parallelStreamPrimeFinder.containsNoPrimes(singleNonPrimeList));
        Assertions.assertFalse(parallelStreamPrimeFinder.containsNoPrimes(endingPrimeList));
        Assertions.assertTrue(parallelStreamPrimeFinder.containsNoPrimes(bigNonPrimesList));
        Assertions.assertTrue(parallelStreamPrimeFinder.containsNoPrimes(longNonPrimesList));
    }

    @Test
    public void nullPointerTest() {
        Assertions.assertThrows(NullPointerException.class,
            () -> PrimeFinder.simplePrimeCheck(null));
        Assertions.assertThrows(NullPointerException.class,
            () -> primeFinder.containsNoPrimes(null));
        Assertions.assertThrows(NullPointerException.class,
            () -> multithreadingPrimeFinder.containsNoPrimes(null));
        Assertions.assertThrows(NullPointerException.class,
            () -> multithreadingPrimeFinder.containsNoPrimes(null, 1));
        Assertions.assertThrows(NullPointerException.class,
            () -> parallelStreamPrimeFinder.containsNoPrimes(null));
    }
}