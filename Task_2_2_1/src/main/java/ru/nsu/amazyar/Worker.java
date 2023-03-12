package ru.nsu.amazyar;

public class Worker implements Runnable{
    private final Pizzeria pizzeria;

    public Worker(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {

    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }
}
