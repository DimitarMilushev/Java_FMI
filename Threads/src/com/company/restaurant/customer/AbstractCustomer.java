package com.company.restaurant.customer;

import com.company.restaurant.Meal;
import com.company.restaurant.Order;
import com.company.restaurant.Restaurant;

import java.util.Random;

public abstract class AbstractCustomer extends Thread{

    private final Restaurant restaurant;
    private final Random random;

    public AbstractCustomer(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.random = new Random();
    }


    @Override
    public void run() {
        try {
            sleep(random.nextInt(4_001 ) + 1_000); //from 1000 to 5000 (ms)

            Order clientOrder = new Order(Meal.chooseFromMenu(), this);
            this.restaurant.submitOrder(clientOrder);

            System.out.printf("%s vip: %b has ordered %s.\n",
                    this.getName(),
                    this.hasVipCard(),
                    clientOrder.meal().getName());

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public abstract boolean hasVipCard();
}
