package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import ru.nsu.amazyar.Order.OrderState;

public class Deliveryman extends Worker{
    private static final long DEFAULT_TRUNK_CAPACITY = 10;

    private final Pizzeria pizzeria;
    private final long trunkCapacity;

    public Deliveryman(Pizzeria pizzeria, long trunkCapacity) {
        super(pizzeria);
        this.pizzeria = pizzeria;
        this.trunkCapacity = trunkCapacity;
    }
    
    public Deliveryman(Pizzeria pizzeria) {
        this(pizzeria, DEFAULT_TRUNK_CAPACITY);
    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    public long getTrunkCapacity() {
        return trunkCapacity;
    }

    @Override
    public void run() {
        List<Order> trunk = new ArrayList<>();
        while(true){
            if(pizzeria.readyPizzas() == 0){
                trunk.add(pizzeria.takePizza());
            }
            else{
                while(pizzeria.readyPizzas() != 0 && trunk.size() < trunkCapacity){
                    trunk.add(pizzeria.takePizza());
                }
            }

            for(Order order : trunk){
                deliver(order);
            }
        }
    }

    public void deliver(Order order){
        try {
            Thread.sleep(order.getTimeToDeliver());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setState(OrderState.DELIVERED);
    }
}
