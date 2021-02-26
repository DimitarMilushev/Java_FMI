package com.company.shopping;

import com.company.shopping.exceptions.ItemNotFoundException;
import com.company.shopping.item.Item;

import java.util.*;

public class MapShoppingCart extends AbstractShoppingCart {

    private final Map<Item, Integer> items;

    public MapShoppingCart (ProductCatalog catalog) {
        super(catalog);
        items = new HashMap<>();
    }

    public Collection<Item> getUniqueItems() {
        Collection<Item> i = new ArrayList<>();
        for(Map.Entry<Item, Integer> entry:items.entrySet()) {
            i.add(entry.getKey());
        }
        return i;
    }

    @Override
    public void addItem(Item item) throws IllegalArgumentException {

        if(this.IsItemNull(item)) {
            throw new IllegalArgumentException(this.ITEM_CANNOT_BE_NULL);
        }

        this.items.put(item, this.items.containsKey(item) ?
                this.items.get(item) + 1: 1);
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (this.IsItemNull(item)) {
            throw new IllegalArgumentException(this.ITEM_CANNOT_BE_NULL);
        }

        if(!items.containsKey(item)) {
            throw new ItemNotFoundException(String.format(this.ITEM_NOT_FOUND, item.getId()));
        }

        boolean isLast = this.items.get(item) < 2;
        // there must be at least one (counting starts from 1)

        if (isLast) {
            items.remove(item);
        } else {
            items.put(item, this.items.get(item) - 1);
        }
    }

    @Override
    public double getTotal() {
        double total = 0;

        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            ProductInfo info = catalog.getProductInfo(entry.getKey().getId());
            total += info.price() * entry.getValue();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> sortedItems = new ArrayList<>(items.keySet());
        Collections.sort(sortedItems, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                ProductInfo info1 = catalog.getProductInfo(item1.getId());
                ProductInfo info2 = catalog.getProductInfo(item2.getId());

                return Double.compare(info2.price(), info1.price());
            }
        });
        return sortedItems;
    }

}