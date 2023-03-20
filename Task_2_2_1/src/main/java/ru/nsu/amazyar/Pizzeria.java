package ru.nsu.amazyar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ru.nsu.amazyar.Order.OrderState;

public class Pizzeria {
    private final ConcurrentDeque<Order> orderQueue;
    private final ConcurrentDeque<Order> storage;
    private final List<Worker> chefs = new ArrayList<>();
    private final List<Worker> deliverymen = new ArrayList<>();

    public Pizzeria(int maxOrders, int storageCapacity, List<Long> chefsOrdersPerMinute, List<Long> deliverymenCapacities) {
        this.orderQueue = new ConcurrentDeque<>(maxOrders);
        this.storage = new ConcurrentDeque<>(storageCapacity);
        for(Long capacity : deliverymenCapacities){
            deliverymen.add(new Deliveryman(this, capacity));
        }
        
        for(Long ordersPerMinute : chefsOrdersPerMinute){
            chefs.add(new Chef(this, ordersPerMinute));
        }
    }

    public int getChefsNumber(){
        return this.chefs.size();
    }

    public int getDeliverymenNumber(){
        return this.deliverymen.size();
    }

    public void order(long timeToDeliver){
        orderQueue.push(new Order(timeToDeliver));
    }

    public Order takeOrder(){
        Order nextOrder = orderQueue.pop();
        nextOrder.setState(OrderState.COOKING);
        return nextOrder;
    }

    public void sendPizza(Order order){
        storage.push(order);
        order.setState(OrderState.STORED);
    }

    public Order takePizza(){
        Order nextOrder = storage.pop();
        nextOrder.setState(OrderState.DELIVERING);
        return nextOrder;
    }

    public int readyPizzas(){
        return storage.size();
    }
}
