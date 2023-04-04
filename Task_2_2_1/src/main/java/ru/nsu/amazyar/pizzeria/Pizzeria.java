package ru.nsu.amazyar.pizzeria;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import ru.nsu.amazyar.utils.ThreadRunner;
import ru.nsu.amazyar.pizzeria.Order.OrderState;
import ru.nsu.amazyar.utils.ConcurrentDeque;

public class Pizzeria {
    private static final Logger logger = LoggerFactory.getLogger(Pizzeria.class);

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

    public void runPizzeria(){
        ThreadRunner.createAndRunThreads(chefs, "Chef");
        ThreadRunner.createAndRunThreads(deliverymen, "Deliveryman");
    }

    public int getChefsNumber(){
        return this.chefs.size();
    }

    public int getDeliverymenNumber(){
        return this.deliverymen.size();
    }

    public void makeOrder(long timeToDeliver){
        Order nextOrder = new Order(timeToDeliver);
        orderQueue.push(nextOrder);
//        logger.info(nextOrder.toString());
        System.out.println("ORDER [" + nextOrder.getOrderId() + "] is " + nextOrder.getState());
    }

    public Order takeOrder(){
        Order nextOrder = orderQueue.pop();
        nextOrder.setState(OrderState.COOKING);
//        logger.info(nextOrder.toString());
        System.out.println("ORDER [" + nextOrder.getOrderId() + "] is " + nextOrder.getState());
        return nextOrder;
    }

    public void storePizza(Order order){
        storage.push(order);
        order.setState(OrderState.STORED);
//        logger.info(order.toString());
        System.out.println("ORDER [" + order.getOrderId() + "] is " + order.getState());
    }

    public Order deliverPizza(){
        Order nextOrder = storage.pop();
        nextOrder.setState(OrderState.DELIVERING);
//        logger.info(nextOrder.toString());
        System.out.println("ORDER [" + nextOrder.getOrderId() + "] is " + nextOrder.getState());
        return nextOrder;
    }

    public int readyPizzas(){
        return storage.size();
    }
}
