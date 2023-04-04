package ru.nsu.amazyar.utils;

import java.util.ArrayDeque;
import java.util.Queue;

public class ConcurrentDeque<T>{
    private final Queue<T> queue;
    private final int capacity;

    public ConcurrentDeque(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    public T pop(){
        synchronized (queue){
            while(queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            T topOrder = queue.poll();
            queue.notifyAll();
            return topOrder;
        }
    }

    public void push(T newOrder) {
        synchronized (queue) {
            while(queue.size() == capacity){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.add(newOrder);
            queue.notifyAll();
        }
    }

    public int size() {
        return queue.size();
    }
}
