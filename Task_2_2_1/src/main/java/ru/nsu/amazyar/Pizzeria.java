package ru.nsu.amazyar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Pizzeria {
    private final long chefsNumber;
    private final long deliverymenNumber;
    private final Queue<Order> orderQueue = new ArrayDeque<>();
    private final Storage<Order> storage;
    private final List<Worker> chefs = new ArrayList<>();
    private final List<Worker> deliverymen = new ArrayList<>();

    public Pizzeria(int chefsNumber, int storageCapacity, List<Long> deliverymenCapacities) {
        this.chefsNumber = chefsNumber;
        this.deliverymenNumber = deliverymenCapacities.size();
        this.storage = new Storage<>(storageCapacity);
        for(Long capacity : deliverymenCapacities){
            deliverymen.add(new Deliveryman(this, capacity));
        }
    }
}
