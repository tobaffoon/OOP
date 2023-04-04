package ru.nsu.amazyar.pizzeria;

/**
 * Takes pizzeria orders and produces pizzas.
 */
public class Chef extends Worker {

    private static final long DEFAULT_ORDER_PER_MINUTE = 3;

    private final Pizzeria pizzeria;
    private final long ordersPerMinute;

    /**
     * Constructor with options.
     *
     * @param ordersPerMinute number pizzas chef cooks in a minute
     */
    public Chef(Pizzeria pizzeria, long ordersPerMinute) {
        super(pizzeria);
        this.pizzeria = pizzeria;
        this.ordersPerMinute = ordersPerMinute;
    }

    /**
     * Default constructor. Sets chef's speed to default value (3)
     */
    public Chef(Pizzeria pizzeria) {
        this(pizzeria, DEFAULT_ORDER_PER_MINUTE);
    }

    /**
     * Pizzeria getter.
     */
    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    /**
     * Chef's speed getter.
     */
    public long getOrdersPerMinute() {
        return ordersPerMinute;
    }

    /**
     * Main routine. Tries to take new order as soon as possible, then cooks it (sleeps) and sends
     * pizza to storage
     */
    @Override
    public void run() {
        while (true) {
            Order nextOrder = pizzeria.takeOrder();

            try {
                Thread.sleep(60000 / ordersPerMinute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pizzeria.storePizza(nextOrder);
        }
    }

}
