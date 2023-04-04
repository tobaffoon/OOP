package ru.nsu.amazyar.pizzeria;

import java.util.Random;

public class Client implements Runnable{
    private static final int MAX_SLEEP_TIME = 30000;
    private static final int MIN_SLEEP_TIME = 5000;

    private Pizzeria pizzeria = null;
    private final int timeToDeliver;

    public Client(Pizzeria pizzeria, int timeToDeliver) {
        this.pizzeria = pizzeria;
        this.timeToDeliver = timeToDeliver;
    }

    public Client(int timeToDeliver) {
        this.timeToDeliver = timeToDeliver * 1000;
    }

    @Override
    public void run() {
        if(pizzeria == null){
            throw new RuntimeException("Cannot execute clients without pizzeria");
        }
        
        Random randomTimeGenerator = new Random(Thread.currentThread().getId()); // randomness will depend on client's thread ID
        while(true){
            try {
                Thread.sleep(randomTimeGenerator.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME) + 5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            pizzeria.makeOrder(timeToDeliver);
        }
    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    public void setPizzeria(Pizzeria pizzeria){
        this.pizzeria = pizzeria;
    }
}
