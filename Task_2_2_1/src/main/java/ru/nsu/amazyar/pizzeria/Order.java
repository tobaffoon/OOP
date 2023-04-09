package ru.nsu.amazyar.pizzeria;

/**
 * Order for Pizzeria.
 */
public class Order {

    /**
     * States of order.
     */
    enum OrderState {
        ORDERED, COOKING, STORED, DELIVERING, DELIVERED,
    }

    private static long ordersCounter = 0;

    private final long orderId;
    private OrderState state;
    private final long timeToDeliver;

    /**
     * Constructor.
     *
     * @param timeToDeliver time for cooked order to be delivered in millis
     */
    public Order(long timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
        this.orderId = ordersCounter++;
        this.state = OrderState.ORDERED;
    }

    /**
     * Get order id.
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Get order state.
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Set order state.
     */
    public void setState(OrderState state) {
        this.state = state;
    }

    /**
     * Get time for order to be delivered.
     */
    public long getTimeToDeliver() {
        return timeToDeliver;
    }

    /**
     * Stringify order id and state.
     */
    @Override
    public String toString() {
        return "[" + orderId + "] " + state.toString();
    }
}
