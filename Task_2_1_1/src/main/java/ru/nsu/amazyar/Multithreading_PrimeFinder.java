package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;

public class Multithreading_PrimeFinder implements PrimeFinder{

    private static final int DEFAULT_THREADS_COUNT = 5;

    private static class PrimeFinderThread extends Thread{
        private final List<Integer> threadList;
        private boolean primeFound = false;

        public PrimeFinderThread(List<Integer> threadList) {
            this.threadList = threadList;
        }

        @Override
        public void run() {
            this.primeFound = PrimeFinder.containsNoPrimes(this.threadList);
        }

        public boolean primeFound() {
            return primeFound;
        }
    }
    public static boolean containsNoPrimes(List<Integer> list, int threadsCount){
        List<PrimeFinderThread> primeFinderThreads = new ArrayList<>(threadsCount);
        int listSize = list.size();
        for (int i = 0; i < threadsCount; i++) {
            PrimeFinderThread pThread = new PrimeFinderThread(list.subList(i * listSize, (i+1) * listSize));
            primeFinderThreads.add(pThread);
            pThread.start();
        }
        for (PrimeFinderThread pThread : primeFinderThreads) {
            try {
                pThread.join();
                if(pThread.primeFound()) return false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    public static boolean containsNoPrimes(List<Integer> list){
        return containsNoPrimes(list, DEFAULT_THREADS_COUNT);
    }
}
