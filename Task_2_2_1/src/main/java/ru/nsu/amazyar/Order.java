package ru.nsu.amazyar;

import java.util.UUID;

public class Order {
    enum OrderState{
        ORDERED,
        COOKED,
        WAITING_FOR_STORAGE,
        STORED,
        DELIVERING,
        DELIVERED,
    }
    private final UUID orderId;
    private final OrderState state;
    private final long timeToCook;
    private final long timeToDeliver;

    public Order(long timeToCook, long timeToDeliver) {
        this.timeToCook = timeToCook;
        this.timeToDeliver = timeToDeliver;
        this.orderId = UUID.randomUUID();
        this.state = OrderState.ORDERED;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderState getState() {
        return state;
    }

    public long getTimeToCook() {
        return timeToCook;
    }

    public long getTimeToDeliver() {
        return timeToDeliver;
    }
}
