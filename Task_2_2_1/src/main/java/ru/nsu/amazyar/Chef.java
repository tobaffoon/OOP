package ru.nsu.amazyar;

public class Chef implements Runnable{
    private final Pizzeria pizzeria;

    public Chef(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {

    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }
}
