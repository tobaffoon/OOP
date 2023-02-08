package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;

public class Multithreading_PrimeFinder extends PrimeFinder{
    private static final int DEFAULT_THREADS_COUNT = 5;
    private boolean primeFound = false;

    private class PrimeFinderThread extends Thread{
        private final List<Integer> threadList;

        public PrimeFinderThread(List<Integer> threadList) {
            this.threadList = threadList;
        }

        @Override
        public void run() {
            if(!containsNoPrimes(threadList)){
                primeFound = true;
            }
        }
    }

    public boolean containsNoPrimes(List<Integer> list, int threadsCount){
        List<PrimeFinderThread> primeFinderThreads = new ArrayList<>(threadsCount + 1);
        int listSize = list.size();
        if(listSize <= threadsCount * 10){
            return super.containsNoPrimes(list);
        }
        int subListStep = listSize / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            PrimeFinderThread pThread = new PrimeFinderThread(list.subList(i * subListStep, (i+1) * subListStep));
            primeFinderThreads.add(pThread);
            pThread.setDaemon(true);            // if some thread found Prime others can terminate immediately
            pThread.start();
        }
        if(listSize % threadsCount != 0){
            PrimeFinderThread pThread = new PrimeFinderThread(list.subList(threadsCount * subListStep, listSize));
            primeFinderThreads.add(pThread);
            pThread.setDaemon(true);
            pThread.start();
        }

        for (PrimeFinderThread pThread : primeFinderThreads) {
            try {
                pThread.join();
                if(primeFound) return false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    public boolean containsNoPrimes(List<Integer> list){
        return containsNoPrimes(list, DEFAULT_THREADS_COUNT);
    }
}
