package ru.nsu.amazyar.pizzeria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ru.nsu.amazyar.pizzeria.Order.OrderState;

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

            Order furthest_order =
                trunk.stream().max(Comparator.comparingLong(Order::getTimeToDeliver)).orElseThrow();

            deliver(furthest_order);
        }
    }

    /**
     * Deliver order. Gets busy for the time specified by the order, then reports about completing
     * it.
     */
    public void deliver(Order order) {
        try {
            Thread.sleep(order.getTimeToDeliver());
            pizzeria.finishOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setState(OrderState.DELIVERED);
    }
}
