package ru.nsu.amazyar.pizzeria;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.amazyar.pizzeria.Order.OrderState;
import ru.nsu.amazyar.utils.ConcurrentQueue;
import ru.nsu.amazyar.utils.ThreadRunner;

/**
 * Pizzeria handling pizzas and orders distribution.
 */
public class Pizzeria {

    private static final Logger logger = LoggerFactory.getLogger(Pizzeria.class);

    private final ConcurrentQueue<Order> orderQueue;
    private final ConcurrentQueue<Order> storage;
    private final List<Worker> chefs = new ArrayList<>();
    private final List<Worker> deliverymen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param maxOrders             max number of orders in queue
     * @param storageCapacity       max number of pizzas in storage
     * @param chefsOrdersPerMinute  chefs' characteristics
     * @param deliverymenCapacities delivery characteristics
     */
    public Pizzeria(int maxOrders, int storageCapacity, List<Long> chefsOrdersPerMinute,
        List<Long> deliverymenCapacities) {
        this.orderQueue = new ConcurrentQueue<>(maxOrders);
        this.storage = new ConcurrentQueue<>(storageCapacity);
        for (Long capacity : deliverymenCapacities) {
            deliverymen.add(new Deliveryman(this, capacity));
        }

        for (Long ordersPerMinute : chefsOrdersPerMinute) {
            chefs.add(new Chef(this, ordersPerMinute));
        }
    }

    /**
     * Create and run chefs and delivery threads.
     */
    public List<Thread> runPizzeriaStaff() {
        List<Thread> chefs_list = ThreadRunner.createAndRunThreads(chefs, "Chef");
        List<Thread> delivery_list = ThreadRunner.createAndRunThreads(deliverymen, "Deliveryman");
        chefs_list.addAll(delivery_list);
        return chefs_list;
    }

    /**
     * Get number of chef threads.
     */
    public int getChefsNumber() {
        return this.chefs.size();
    }

    /**
     * Get number of delivery threads.
     */
    public int getDeliverymenNumber() {
        return this.deliverymen.size();
    }

    /**
     * Submit new order.
     *
     * @param timeToDeliver time for cooked order to be delivered
     */
    public void makeOrder(long timeToDeliver) {
        Order nextOrder = new Order(timeToDeliver);
        orderQueue.push(nextOrder);
        synchronized (nextOrder) {
            logger.info(nextOrder.toString());
        }
    }

    /**
     * Take an order from orders' queue.
     */
    public Order takeOrder() {
        Order nextOrder = orderQueue.pop();
        synchronized (nextOrder) {
            nextOrder.setState(OrderState.COOKING);
            logger.info(nextOrder.toString());
        }
        return nextOrder;
    }

    /**
     * Submit new pizza (an order state) to storage.
     *
     * @param order order for which pizza has been cooked
     */
    public void storePizza(Order order) {
        storage.push(order);
        synchronized (order) {
            order.setState(OrderState.STORED);
            logger.info(order.toString());
        }
    }

    /**
     * Take pizza from storage to deliver it.
     */
    public Order deliverPizza() {
        Order nextOrder = storage.pop();
        synchronized (nextOrder) {
            nextOrder.setState(OrderState.DELIVERING);
            logger.info(nextOrder.toString());
        }
        return nextOrder;
    }

    /**
     * Report about delivering pizza.
     */
    public void finishOrder(Order order) {
        synchronized (order) {
            order.setState(OrderState.DELIVERED);
            logger.info(order.toString());
        }
    }

    /**
     * Get number of pizzas in the storage.
     */
    public int readyPizzas() {
        return storage.size();
    }
}
