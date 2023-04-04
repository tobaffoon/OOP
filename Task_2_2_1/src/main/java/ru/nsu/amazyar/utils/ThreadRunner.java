package ru.nsu.amazyar.utils;

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
    public static void createAndRunThreads(List<? extends Runnable> runnables,
        String nameTemplate) {
        for (int i = 0; i < runnables.size(); i++) {
            Thread new_thread = new Thread(runnables.get(i), nameTemplate + "-" + i);
            new_thread.start();
        }
    }
}
