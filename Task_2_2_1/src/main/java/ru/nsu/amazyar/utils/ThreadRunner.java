package ru.nsu.amazyar.utils;

import java.util.List;

public class ThreadRunner {
    public static void createAndRunThreads(List<? extends Runnable> runnables, String nameTemplate){
        for(int i = 0; i < runnables.size(); i++){
            Thread new_thread = new Thread(runnables.get(i), nameTemplate + "-" + i);
            new_thread.start();
        }
    }
}
