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

    public Order() {
        this.orderId = UUID.randomUUID();
        this.state = OrderState.ORDERED;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderState getState() {
        return state;
    }
}
