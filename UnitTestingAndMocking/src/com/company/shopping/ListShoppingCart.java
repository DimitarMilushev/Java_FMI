package com.company.shopping;

import com.company.shopping.exceptions.ItemNotFoundException;
import com.company.shopping.item.Item;

import java.util.*;

public class ListShoppingCart extends AbstractShoppingCart {

    private final ArrayList<Item> items;

    public ListShoppingCart(ProductCatalog catalog) {
        super(catalog);
        this.items = new ArrayList<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return new HashSet<>(items);
    }

    @Override
    public void addItem(Item item) throws IllegalArgumentException{
        if(IsItemNull(item)) {
            throw new IllegalArgumentException(this.ITEM_CANNOT_BE_NULL);
        }

        items.add(item);
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException{
        if(IsItemNull(item)) {
            throw new IllegalArgumentException(this.ITEM_CANNOT_BE_NULL);
        }

        if(!this.items.contains(item)) {
            throw new ItemNotFoundException(String.format(this.ITEM_NOT_FOUND, item.getId()));
        }

        items.remove(item);
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            ProductInfo info = catalog.getProductInfo(item.getId());
            total += info.price();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        Map<Item, Integer> itemToQuantity = create_map();
        Map<Item, Integer> sortedItems = new TreeMap<>(new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                return itemToQuantity.get(item2).compareTo(itemToQuantity.get(item1));
            }
        });
        sortedItems.putAll(itemToQuantity);
        return sortedItems.keySet();
    }

    private Map<Item, Integer> create_map() {
        HashMap<Item, Integer> itemToQuantity = new HashMap<Item, Integer>();

        for (Item item : items) {
            boolean condition = itemToQuantity.containsKey(item);
            itemToQuantity.put(item, condition ? itemToQuantity.get(item) + 1: 1);
        }
        return itemToQuantity;
    }
}