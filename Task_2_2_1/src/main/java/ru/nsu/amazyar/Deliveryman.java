package ru.nsu.amazyar;

public class Deliveryman implements Runnable{
    private final Pizzeria pizzeria;
    private final long trunkCapacity;

    public Deliveryman(Pizzeria pizzeria, long trunkCapacity) {
        this.pizzeria = pizzeria;
        this.trunkCapacity = trunkCapacity;
    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    public long getTrunkCapacity() {
        return trunkCapacity;
    }

    @Override
    public void run() {

    }
}
