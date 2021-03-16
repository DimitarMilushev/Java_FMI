package com.company.restaurant;

import com.company.restaurant.customer.AbstractCustomer;

import java.util.Comparator;

public record Order (Meal meal, AbstractCustomer customer) implements Comparable<Order>{

    @Override
    public int compareTo(Order o) {
        int cardComp = Boolean.compare
                (customer.hasVipCard(), o.customer.hasVipCard());
        if (cardComp != 0) {
            return cardComp;
        }

        return Integer.compare(meal.getCookingTime(), o.meal.getCookingTime());
    }
}