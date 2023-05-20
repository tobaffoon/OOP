package ru.nsu.amazyar.bases;

import javafx.animation.AnimationTimer;

public class SnakeTimer extends AnimationTimer {
    private long lastHandledTimeNanos = 0;
    private double fps = 60;
    private double frameInterval = 1000000000/fps;
    private double delta = 0;
    private long lastTime = System.nanoTime();
    long currentTime;

    @Override
    public void handle(long now) {
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / frameInterval;  // each whole number equals one frame
        lastTime = currentTime;

        if(delta >= 1){
            delta--;
        }
    }
}
