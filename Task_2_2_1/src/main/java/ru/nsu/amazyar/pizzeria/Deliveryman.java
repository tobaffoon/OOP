package ru.nsu.amazyar.pizzeria;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.amazyar.pizzeria.Order.OrderState;

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
                trunk.add(pizzeria.deliverPizza());
            }
            else{
                while(pizzeria.readyPizzas() != 0 && trunk.size() < trunkCapacity){
                    trunk.add(pizzeria.deliverPizza());
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
            pizzeria.finishOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setState(OrderState.DELIVERED);
    }
}
