package ru.nsu.amazyar;

public class Deliveryman extends Worker{
    private static final long DEFAULT_TRUNK_CAPACITY = 10;

    private final Pizzeria pizzeria;
    private final long trunkCapacity;

    public Deliveryman(Pizzeria pizzeria, long trunkCapacity) {
        super(pizzeria);
        this.pizzeria = pizzeria;
        this.trunkCapacity = trunkCapacity;
    }
    
    public Deliveryman(Pizzeria pizzeria) {
        this(pizzeria, DEFAULT_TRUNK_CAPACITY);
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
