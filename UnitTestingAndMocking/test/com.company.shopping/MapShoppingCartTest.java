package com.company.shopping;

import com.company.shopping.exceptions.ItemNotFoundException;
import com.company.shopping.item.*;
import com.company.shopping.ProductCatalog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapShoppingCartTest {

    private final AbstractItem chocolateItem = new Chocolate("1");
    private final AbstractItem appleItem = new Apple("2");
    private final AbstractItem pearItem = new Pear("3");

    @Mock
    private ProductCatalog catalog;

    @InjectMocks
    private MapShoppingCart shoppingCart;

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemShouldThrowIllegalArgumentException() {
        this.shoppingCart.addItem(null);
    }

    @Test
    public void testGetUniqueItemsShouldReturnUniqueItems() {
        this.shoppingCart.addItem(chocolateItem);
        this.shoppingCart.addItem(chocolateItem);
        this.shoppingCart.addItem(appleItem);

        Item[] expected = new Item[]{chocolateItem, appleItem};
        Object[] actual = this.shoppingCart.getUniqueItems().toArray();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetUniqueItemsShouldReturnEmptyCollectionWhenEmpty() {
        Item[] expected = new Item[]{};
        Object[] actual = this.shoppingCart.getUniqueItems().toArray();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddItemShouldAddGivenItem() {
        this.shoppingCart.addItem(chocolateItem);

        Item[] expected = new Item[]{chocolateItem};
        Object[] actual = this.shoppingCart.getUniqueItems().toArray();
        assertArrayEquals("Item should get added.",expected, actual);
    }

    @Test
    public void testAddItemShouldAddOnlyGivenItem() {
        this.shoppingCart.addItem(chocolateItem);

        Item[] expected = new Item[] {chocolateItem};
        Object[] actual = this.shoppingCart.getUniqueItems().toArray();

        assertNotSame("If items are different then it works right.",expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemShouldThrowIllegalArgumentException() {
        this.shoppingCart.removeItem(null);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemShouldThrowItemNotFoundException() {
        this.shoppingCart.addItem(chocolateItem);
        this.shoppingCart.removeItem(appleItem);
    }

    @Test
    public void testRemoveItemShouldRemoveItem() {
        this.shoppingCart.addItem(chocolateItem);
        this.shoppingCart.addItem(pearItem);

        this.shoppingCart.removeItem(chocolateItem);

        Object[] actual = this.shoppingCart.getUniqueItems().toArray();
        assertEquals(
                "If chocolate is deleted, then pear should be the only item(0).",pearItem, actual[0]);

    }

    @Test
    public void testGetTotalShouldReturnTotalCost() {
        this.shoppingCart.addItem(chocolateItem);
        this.shoppingCart.addItem(pearItem);
        this.shoppingCart.addItem(pearItem);

        when(this.catalog.getProductInfo("1"))
                .thenReturn(new ProductInfo("Chocolate", "Nutella", 10.11d));

        when(this.catalog.getProductInfo("3"))
                .thenReturn(new ProductInfo("Pear", "Ripe", 0.50d));

        double expected = 11.11d;
        double actual = this.shoppingCart.getTotal();

        assertEquals(
                "When added 10.11 and 0.5 * 2(quantity) it should equal 11.11", expected, actual, 0.00d);
    }

    @Test
    public void testGetSortedItemsShouldReturnOrderedByCost() {
        shoppingCart.addItem(chocolateItem);
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(pearItem);

        when(this.catalog.getProductInfo("1"))
                .thenReturn(new ProductInfo("Chocolate", "Nutella", 10.2d));

        when(this.catalog.getProductInfo("2"))
                .thenReturn(new ProductInfo("Apple", "Ripe", 0.3d));

        when(this.catalog.getProductInfo("3"))
                .thenReturn(new ProductInfo("Pear", "Unknown", 0.5d));


        Object[] expected = new Item[] {chocolateItem, pearItem, appleItem};
        Object[] actual = shoppingCart.getSortedItems().toArray();
        /**
         * chocolate value = 10.2
         * pear value = 0.5
         * apple value = 0.3
         */

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetSortedItemsShouldReturnEmptyList() {
        Object[] actual = shoppingCart.getSortedItems().toArray();

        assertArrayEquals(new Item[]{}, actual);
    }
}
