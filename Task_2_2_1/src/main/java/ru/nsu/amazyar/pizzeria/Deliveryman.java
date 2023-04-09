package ru.nsu.amazyar.pizzeria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Delivers orders from pizzeria.
 */
public class Deliveryman extends Worker {

    private static final long DEFAULT_TRUNK_CAPACITY = 1;

    private final Pizzeria pizzeria;
    private final long trunkCapacity;

    /**
     * Constructor with options.
     *
     * @param trunkCapacity max number of pizzas per delivery
     */
    public Deliveryman(Pizzeria pizzeria, long trunkCapacity) {
        super(pizzeria);
        this.pizzeria = pizzeria;
        this.trunkCapacity = trunkCapacity;
    }

    /**
     * Default constructor. Sets trunk capacity to default value (1)
     */
    public Deliveryman(Pizzeria pizzeria) {
        this(pizzeria, DEFAULT_TRUNK_CAPACITY);
    }

    /**
     * Pizzeria getter.
     */
    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    /**
     * Trunk capacity getter.
     */
    public long getTrunkCapacity() {
        return trunkCapacity;
    }

    /**
     * Main routine. Gets max available number of pizzas that will fill it the trunk, or if there is
     * no pizzas waits for just one pizza. Delivers them afterwards.
     */
    @Override
    public void run() {
        List<Order> trunk = new ArrayList<>();
        while (true) {
            if (pizzeria.readyPizzas() == 0) {
                trunk.add(pizzeria.deliverPizza());
            } else {
                while (pizzeria.readyPizzas() != 0 && trunk.size() < trunkCapacity) {
                    trunk.add(pizzeria.deliverPizza());
                }
            }

            deliver(trunk);
        }
    }

    /**
     * Deliver order.
     * Gets busy for the time specified by the order which need
     * to be delivered the furthest from pizzeria,
     * then reports about completing all the orders, whose pizzas were in his trunk.
     */
    public void deliver(List<Order> trunk) {
        try {
            Order furthestOrder =
                trunk.stream().max(Comparator.comparingLong(Order::getTimeToDeliver)).orElseThrow();
            Thread.sleep(furthestOrder.getTimeToDeliver());

            for (Order order : trunk) {
                pizzeria.finishOrder(order);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
