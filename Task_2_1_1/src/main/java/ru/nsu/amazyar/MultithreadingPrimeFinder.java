package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks list for primes via multithreading.
 */
public class MultithreadingPrimeFinder extends PrimeFinder {

    private static final int DEFAULT_THREADS_COUNT = 5;
    private boolean primeFound = false;

    private class PrimeFinderThread extends Thread {

        private final List<Integer> threadList;

        public PrimeFinderThread(List<Integer> threadList) {
            this.threadList = threadList;
        }

        @Override
        public void run() {
            if (!MultithreadingPrimeFinder.super.containsNoPrimes(threadList)) {
                setPrimeFound(true);
            }
        }
    }

    private synchronized void setPrimeFound(boolean value) {
        this.primeFound = value;
    }

    private synchronized boolean getPrimeFound() {
        return this.primeFound;
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
        this.setPrimeFound(false);

        //----------Small lists trivial case----------
        if (listSize <= threadsCount * 10) {
            return super.containsNoPrimes(list);
        }

        //----------Thread creation----------
        int subListStep = listSize / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            PrimeFinderThread primeThread =
                new PrimeFinderThread(list.subList(i * subListStep, (i + 1) * subListStep));
            primeFinderThreads.add(primeThread);
            //thread can terminate immediately if a prime number's found (main thread exits)
            primeThread.setDaemon(true);
            primeThread.start();
        }
        if (listSize % threadsCount != 0) { //add extra thread to take care of end of the list
            PrimeFinderThread pThread =
                new PrimeFinderThread(list.subList(threadsCount * subListStep, listSize));
            primeFinderThreads.add(pThread);
            pThread.setDaemon(true);
            pThread.start();
        }

        //----------Process threads results----------
        for (PrimeFinderThread pThread : primeFinderThreads) {
            try {
                pThread.join();
                if (getPrimeFound()) {
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
    public boolean containsNoPrimes(List<Integer> list) {
        return containsNoPrimes(list, DEFAULT_THREADS_COUNT);
    }
}
