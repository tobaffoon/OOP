package ru.nsu.amazyar;

public class Chef extends Worker{
    private static final long DEFAULT_ORDER_PER_MINUTE = 3;

    private final Pizzeria pizzeria;
    private final long ordersPerMinute;

    public Chef(Pizzeria pizzeria, long ordersPerMinute) {
        super(pizzeria);
        this.pizzeria = pizzeria;
        this.ordersPerMinute = ordersPerMinute;
    }

    public Chef(Pizzeria pizzeria) {
        this(pizzeria, DEFAULT_ORDER_PER_MINUTE);
    }

    public Pizzeria getPizzeria() {
        return pizzeria;
    }

    public long getOrdersPerMinute(){
        return ordersPerMinute;
    }

    @Override
    public void run() {
        while(true){
            Order nextOrder = pizzeria.takeOrder();

            try {
                Thread.sleep(60000 / ordersPerMinute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pizzeria.sendPizza(nextOrder);
        }
    }

}
