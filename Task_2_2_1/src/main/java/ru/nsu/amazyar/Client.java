package ru.nsu.amazyar;

public class Client implements Runnable{
    private final Pizzeria pizzeria;

    public Client(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {

    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }
}
