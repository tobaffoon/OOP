package ru.nsu.amazyar.bases;

import javafx.animation.AnimationTimer;

public class CycleTimer extends AnimationTimer {
    private final long frameInterval;
    private double delta;
    private long lastTime;
    private boolean timerWasStopped;
    private final Runnable callback;

    /**
     * Creates a timer that calls callback
     * @param nanos nanoseconds between callback calls
     */
    public CycleTimer(long nanos, Runnable callback) {
        this.frameInterval = nanos;
        this.delta = 0;
        this.callback = callback;
        this.lastTime = System.nanoTime();
        this.timerWasStopped = false;
    }


    @Override
    public void handle(long now) {
        if(timerWasStopped){
            lastTime = now;
            timerWasStopped = false;
        }
        delta += (now - lastTime) / (double) frameInterval;  // each whole number equals one frame
        lastTime = now;

        if(delta >= 1){
            delta--;
            callback.run();
        }
    }

    @Override
    public void stop(){
        this.timerWasStopped = true;
        super.stop();
    }

    public long getNanosInterval() {
        return frameInterval;
    }
}