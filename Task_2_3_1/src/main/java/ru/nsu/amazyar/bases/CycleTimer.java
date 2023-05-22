package ru.nsu.amazyar.bases;

import javafx.animation.AnimationTimer;

public class CycleTimer extends AnimationTimer {
    private double fps;
    private double frameInterval;
    private double delta = 0;
    private long lastTime;
    private Runnable callback;
    private Runnable bufferCallback;
    private static Runnable spinlock = () -> {};

    /**
     * Creates a timer that calls callback
     * @param nanos nanoseconds between callback calls
     */
    public CycleTimer(long nanos, Runnable callback) {
        this.frameInterval = nanos;
        this.callback = callback;
        this.lastTime = System.nanoTime();
        this.bufferCallback = callback;
    }


    @Override
    public void handle(long now) {
        delta += (now - lastTime) / frameInterval;  // each whole number equals one frame
        lastTime = now;

        if(delta >= 1){
            delta--;
            callback.run();
        }
    }

    public void turnOff(){
        callback = spinlock;
    }

    public void turnOn(){
        callback = bufferCallback;
    }
}