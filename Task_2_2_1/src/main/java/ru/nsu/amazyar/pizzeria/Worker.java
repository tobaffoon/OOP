package ru.nsu.amazyar.pizzeria;

/**
 * Pizzeria worker.
 */
public abstract class Worker implements Runnable {

    private final Pizzeria pizzeria;

    /**
     * Constructor with assigned pizzeria.
     */
    public Worker(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    /**
     * Main routine.
     */
    public abstract void run();

    /**
     * Pizzeria getter
     */
    public Pizzeria getPizzeria() {
        return pizzeria;
    }
}
