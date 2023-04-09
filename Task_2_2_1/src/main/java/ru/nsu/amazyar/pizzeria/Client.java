package ru.nsu.amazyar.pizzeria;

import java.util.Random;

/**
 * Makes new orders for pizzeria.
 */
public class Client implements Runnable {

    private static final int MAX_SLEEP_TIME = 30000;
    private static final int MIN_SLEEP_TIME = 5000;

    private Pizzeria pizzeria = null;
    private final int timeToDeliver;

    private boolean consistentSleep = false;
    private int consistentSleepTime;

    /**
     * Constructor with bound pizzeria.
     *
     * @param timeToDeliver time for cooked order to be delivered in millis
     */
    public Client(Pizzeria pizzeria, int timeToDeliver) {
        this.pizzeria = pizzeria;
        this.timeToDeliver = timeToDeliver * 1000;
    }

    /**
     * Constructor without bound pizzeria.
     *
     * @param timeToDeliver time for cooked order to be delivered to the client in millis
     */
    public Client(int timeToDeliver) {
        this.timeToDeliver = timeToDeliver * 1000;
    }

    /**
     * Main routine. Sleeps for some time, then creates one order and sleeps again
     */
    @Override
    public void run() {
        if (pizzeria == null) {
            throw new RuntimeException("Cannot execute clients without pizzeria");
        }

        Random randomTimeGenerator = new Random(
            Thread.currentThread().getId()); // randomness will depend on client's thread ID
        while (true) {
            try {
                if (consistentSleep) {
                    Thread.sleep(consistentSleepTime);
                } else {
                    Thread.sleep(randomTimeGenerator.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME)
                        + MIN_SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pizzeria.makeOrder(timeToDeliver);
        }
    }

    /**
     * Pizzeria getter.
     */
    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    /**
     * Pizzeria setter.
     */
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    /**
     * Set mode in which client sleeps for constant time before making an order.
     *
     * @param sleepTime time for client to sleep between orders
     */
    public void setConsistentSleep(int sleepTime) {
        consistentSleep = true;

        if (sleepTime < 0) {
            sleepTime = 0;
        }
        consistentSleepTime = sleepTime;
    }
}
