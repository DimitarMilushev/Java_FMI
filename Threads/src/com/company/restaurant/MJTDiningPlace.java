package com.company.restaurant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MJTDiningPlace implements Restaurant{

    private final String NULL_EXCEPTION_SUFFIX = " cannot be null!";

    private final Chef[] chefs;
    private final Queue<Order> ordersList;
    private boolean isClosed;
    private final AtomicInteger totalOrders;


    public MJTDiningPlace (int chefsCount) {
        ordersList = new PriorityQueue<Order>(Collections.reverseOrder());
        isClosed = false;
        totalOrders = new AtomicInteger(0);
        this.chefs = new Chef[chefsCount];
        initializeChefsList();
    }
//    private final Function<Order, Boolean> getByVipCard = order -> order.customer().hasVipCard();
//    private final Function<Order, Integer> getByCookingTime = order -> order.meal().getCookingTime();

    @Override
    public void submitOrder(Order order) {
        if(!this.isClosed) {
            this.ordersList.add(order);

            synchronized (this) {
                notifyAll();
            }
            totalOrders.incrementAndGet();
        }
    }

    @Override
    public synchronized Order nextOrder() {

        while(this.ordersList.isEmpty() && !this.isClosed) {
            try {
                wait();
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return this.ordersList.isEmpty() ? null : this.ordersList.poll();
    }

    @Override
    public int getOrdersCount() {
        return this.totalOrders.get();
    }

    public void printOrders() {
        System.out.println(this.ordersList.toString());
    }

    @Override
    public Chef[] getChefs() {
        return this.chefs;
    }

    @Override
    public synchronized void close() {

        this.isClosed = true;
        notifyAll();
    }

    private void initializeChefsList() {
        for (int i = 0; i < this.chefs.length; i++) {
            this.chefs[i] = new Chef(Chef.getNewId(), this);
            this.chefs[i].start();
        }
    }
}
