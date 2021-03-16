package com.company.restaurant;

import java.util.concurrent.atomic.AtomicInteger;

public class Chef extends Thread {

    public static AtomicInteger chefsCount = new AtomicInteger(0);
    private int mealsCooked;
    private final int id;
    private final Restaurant restaurant;

    public Chef(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
        this.mealsCooked = 0;

        this.setName("Chef " + this.id);
    }

    @Override
    public void run() {
        Order order;

        while((order = this.restaurant.nextOrder()) != null) {

            try {
                Thread.sleep(order.meal().getCookingTime() * 100L);

            } catch (InterruptedException ex) {
                System.out.println("Thread " + this.id + "was interrupted!");
            }

            System.out.printf("%s has cooked meal %s.\n",
                    this.getName(), order.meal().getName());
            System.out.println(++this.mealsCooked);
        }

    }

    /**
     * Returns the total number of meals that this chef has cooked.
     **/
    public int getTotalCookedMeals() {
        return this.mealsCooked;
    }

    public static int getNewId() {
        return chefsCount.getAndIncrement();
    }

}