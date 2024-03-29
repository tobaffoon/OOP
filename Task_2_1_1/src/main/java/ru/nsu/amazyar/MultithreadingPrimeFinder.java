package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Checks list for primes via multithreading.
 */
public class MultithreadingPrimeFinder extends PrimeFinder {

    private static final int DEFAULT_THREADS_COUNT = 5;
    private final AtomicBoolean primeFound = new AtomicBoolean();

    private class PrimeFinderThread extends Thread {

        private final List<Integer> threadList;

        public PrimeFinderThread(List<Integer> threadList) {
            this.threadList = threadList;
        }

        @Override
        public void run() {
            if (!MultithreadingPrimeFinder.super.containsNoPrimes(threadList)) {
                MultithreadingPrimeFinder.this.primeFound.set(true);
            }
        }
    }

    /**
     * Checks for primes in list.
     */
    public boolean containsNoPrimes(List<Integer> list, int threadsCount) {
        if (list == null) {
            throw new NullPointerException();
        }
        if (threadsCount == 0) {
            throw new IllegalArgumentException("Can't have 0 threads in process");
        }
        List<PrimeFinderThread> primeFinderThreads = new ArrayList<>(threadsCount + 1);
        int listSize = list.size();
        this.primeFound.set(false);

        //----------Thread creation----------
        List<Integer> subList;
        int subListStep = listSize / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            subList = list.subList(i * subListStep, (i + 1) * subListStep);
            createAndRunPrimeFinder(subList, primeFinderThreads);
        }
        if (listSize % threadsCount != 0) { //add extra thread to take care of end of the list
            subList = list.subList(threadsCount * subListStep, listSize);
            createAndRunPrimeFinder(subList, primeFinderThreads);
        }

        //----------Process threads results----------
        for (PrimeFinderThread primeThread : primeFinderThreads) {
            try {
                primeThread.join();
                if (this.primeFound.get()) {
                    return false;
                }
            } catch (InterruptedException ignored) {
            }
        }
        return true;
    }

    /**
     * Checks for primes in list.
     */
    @Override
    public boolean containsNoPrimes(List<Integer> list) {
        return containsNoPrimes(list, DEFAULT_THREADS_COUNT);
    }

    private void createAndRunPrimeFinder(List<Integer> list, List<PrimeFinderThread> threadList) {
        PrimeFinderThread primeThread =
            new PrimeFinderThread(list);
        threadList.add(primeThread);
        primeThread.setDaemon(true);
        primeThread.start();
    }
}
