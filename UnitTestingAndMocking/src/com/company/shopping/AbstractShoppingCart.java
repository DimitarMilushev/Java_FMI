package com.company.shopping;

import com.company.shopping.item.Item;

public abstract class AbstractShoppingCart implements ShoppingCart {
    protected final String ITEM_CANNOT_BE_NULL = "Item cannot be null!";
    protected final String ITEM_NOT_FOUND = "Item with id %s cannot be found!";

    protected ProductCatalog catalog;

    public AbstractShoppingCart(ProductCatalog catalog) {

        this.catalog = catalog;
    }

    protected boolean IsItemNull(Item item) {

        if(item == null) {
            return true;
        }

        return false;
    }
}
