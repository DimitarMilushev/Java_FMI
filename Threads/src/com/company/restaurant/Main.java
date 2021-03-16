package com.company.restaurant;

import com.company.restaurant.customer.AbstractCustomer;
import com.company.restaurant.customer.Customer;
import com.company.restaurant.customer.VipCustomer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MJTDiningPlace diningPlace = new MJTDiningPlace(10);
        AbstractCustomer[] customers = new AbstractCustomer[10];

        Thread closingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    diningPlace.close();
                    diningPlace.printOrders();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < 5; i++) {
            customers[i] = new Customer(diningPlace);
            customers[5 + i] = new VipCustomer(diningPlace);
        }

        closingThread.start();
            for (AbstractCustomer c : customers) {
                c.start();
            }


    }
}
