package ru.nsu.amazyar.bases;

import javafx.animation.AnimationTimer;

public class CycleTimer extends AnimationTimer {
    private double fps;
    private double frameInterval;
    private double delta = 0;
    private long lastTime;
    private Runnable callback;

    public CycleTimer(double fps, Runnable callback) {
        this.fps = fps;
        this.frameInterval = 1000000000/fps;
        this.callback = callback;
        this.lastTime = System.nanoTime();
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
}