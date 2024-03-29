package ru.nsu.amazyar.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates and runs threads.
 */
public class ThreadRunner {

    /**
     * Create and run new threads.
     *
     * @param runnables    programs for the threads
     * @param nameTemplate prefix of names for newly created threads
     */
    public static List<Thread> createAndRunThreads(List<? extends Runnable> runnables,
        String nameTemplate) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < runnables.size(); i++) {
            Thread newThread = new Thread(runnables.get(i), nameTemplate + "-" + i);
            newThread.start();
            threadList.add(newThread);
        }
        return threadList;
    }
}
