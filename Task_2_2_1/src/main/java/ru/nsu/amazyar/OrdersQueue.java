package ru.nsu.amazyar;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrdersQueue<T> {
    private final Queue<T> queue = new ArrayDeque<>(MAXSIZE);
    private final Object EMPTY_QUEUE = new Object();
    private final Object FULL_QUEUE = new Object();
    private static final int MAXSIZE = 150;

    public OrdersQueue() {
    }

    public T get() throws InterruptedException {
        synchronized (EMPTY_QUEUE){
            while(queue.isEmpty()){
                EMPTY_QUEUE.wait();
            }

            T topOrder = queue.poll();
            FULL_QUEUE.notifyAll();
            return topOrder;
        }
    }

    public void push(T newOrder) throws InterruptedException {
        synchronized (FULL_QUEUE){
            while(queue.size() == MAXSIZE){
                FULL_QUEUE.wait();
            }

            queue.add(newOrder);
            EMPTY_QUEUE.notifyAll();
        }
    }
}
