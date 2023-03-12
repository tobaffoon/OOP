package ru.nsu.amazyar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Pizzeria {
    private final int chefsNumber;
    private final int deliverymenNumber;
    private final Queue<Order> orderQueue = new ArrayDeque<>();
    private final Storage<Order> storage;
    private final List<Worker> chefs = new ArrayList<>();
    private final List<Worker> deliverymen = new ArrayList<>();

    public Pizzeria(int chefsNumber, int deliverymenNumber, int storageCapacity) {
        this.chefsNumber = chefsNumber;
        this.deliverymenNumber = deliverymenNumber;
        this.storage = new Storage<>(storageCapacity);
    }
}
