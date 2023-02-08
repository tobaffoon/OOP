package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks list for primes via multithreading.
 */
public class Multithreading_PrimeFinder extends PrimeFinder {

    private static final int DEFAULT_THREADS_COUNT = 5;
    private boolean primeFound = false;

    private class PrimeFinderThread extends Thread {

        private final List<Integer> threadList;

        public PrimeFinderThread(List<Integer> threadList) {
            this.threadList = threadList;
        }

        @Override
        public void run() {
            if (Multithreading_PrimeFinder.super.containsNoPrimes(threadList)) {
                primeFound = true;
            }
        }
    }

    public boolean containsNoPrimes(List<Integer> list, int threadsCount) {
        List<PrimeFinderThread> primeFinderThreads = new ArrayList<>(threadsCount + 1);
        int listSize = list.size();

        //----------Small lists trivial case----------
        if (listSize <= threadsCount * 10) {
            return super.containsNoPrimes(list);
        }

        //----------Thread creation----------
        int subListStep = listSize / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            PrimeFinderThread pThread =
                new PrimeFinderThread(list.subList(i * subListStep, (i + 1) * subListStep));
            primeFinderThreads.add(pThread);
            //thread can terminate immediately if a prime number's found (main thread exits)
            pThread.setDaemon(true);
            pThread.start();
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
                if (primeFound) {
                    return false;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
