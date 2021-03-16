package com.company.restaurant.customer;

import com.company.restaurant.Restaurant;

public class Customer extends AbstractCustomer{
    public Customer(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public boolean hasVipCard() {
        return false;
    }
}
