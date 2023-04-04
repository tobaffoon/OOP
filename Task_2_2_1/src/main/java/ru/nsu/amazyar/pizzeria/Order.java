package ru.nsu.amazyar.pizzeria;

import java.util.UUID;

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

    private final UUID orderId;
    private OrderState state;
    private final long timeToDeliver;

    /**
     * Constructor.
     *
     * @param timeToDeliver time for cooked order to be delivered in millis
     */
    public Order(long timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
        this.orderId = UUID.randomUUID();
        this.state = OrderState.ORDERED;
    }

    /**
     * Get order id.
     */
    public UUID getOrderId() {
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
        return "[" + orderId.toString() + "] " + state.toString();
    }
}
