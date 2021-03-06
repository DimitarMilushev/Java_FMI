package com.company.restaurant;

import java.util.Formattable;
import java.util.concurrent.Callable;
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


    }

    public class PrepOrder implements Callable<String> {

        @Override
        public String call() throws Exception {
            Order order = restaurant.nextOrder();

                try {
                    Thread.sleep(order.meal().getCookingTime() * 100L);

                } catch (InterruptedException ex) {
                    System.out.println("Thread " + id + "was interrupted!");
                }

                return String.format("""
                            %s has cooked meal %s.
                            %s has cooked %d meals.
                                     """,
                        getName(), order.meal().getName(), ++mealsCooked);
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