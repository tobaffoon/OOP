package ru.nsu.amazyar;

import java.util.UUID;

public class Order {
    enum OrderState{
        ORDERED,
        COOKING,
        STORED,
        DELIVERING,
        DELIVERED,
    }
    private final UUID orderId;
    private OrderState state;
    private final long timeToDeliver;

    public Order(long timeToDeliver) {
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

    public void setState(OrderState state){
        this.state = state;
    }

    public long getTimeToDeliver() {
        return timeToDeliver;
    }
}
