package ru.nsu.amazyar;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrdersQueue<T> {
    private final Queue<T> queue = new ArrayDeque<>(MAXSIZE);
    private static final int MAXSIZE = 150;

    public OrdersQueue() {
    }

    public T get() throws InterruptedException {
        synchronized (queue){
            while(queue.isEmpty()){
                queue.wait();
            }

            T topOrder = queue.poll();
            queue.notifyAll();
            return topOrder;
        }
    }

    public void push(T newOrder) throws InterruptedException {
        synchronized (queue){
            while(queue.size() == MAXSIZE){
                queue.wait();
            }

            queue.add(newOrder);
            queue.notifyAll();
        }
    }
}
