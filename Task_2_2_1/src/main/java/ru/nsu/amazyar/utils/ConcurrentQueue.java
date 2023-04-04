package ru.nsu.amazyar.utils;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Self-made concurrent queue.
 */
public class ConcurrentQueue<T> {

    private final Queue<T> queue;
    private final int capacity;

    /**
     * Constructor.
     *
     * @param capacity max number of elements
     */
    public ConcurrentQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    /**
     * Synchronous pop.
     */
    public T pop() {
        synchronized (queue) {
            while (queue.isEmpty()) {
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

    /**
     * Synchronous push.
     */
    public void push(T newOrder) {
        synchronized (queue) {
            while (queue.size() == capacity) {
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

    /**
     * Get number of elements in queue.
     */
    public int size() {
        return queue.size();
    }
}
