package ru.nsu.amazyar;

import java.util.Random;

public class Client implements Runnable{
    private static final int MAX_SLEEP_TIME = 30;
    private static final int MAX_DELIVERY_TIME = 45;

    private final Pizzeria pizzeria;

    public Client(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        Random randomTimeGenerator = new Random(Thread.currentThread().getId()); // randomness will depend on client's thread ID
        while(true){
            try {
                Thread.sleep(randomTimeGenerator.nextInt(MAX_SLEEP_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            pizzeria.order(randomTimeGenerator.nextInt(MAX_DELIVERY_TIME));
        }
    }
    public Pizzeria getPizzeria() {
        return pizzeria;
    }
}
