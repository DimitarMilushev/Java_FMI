package com.company.shopping;

import com.company.shopping.exceptions.ItemNotFoundException;
import com.company.shopping.item.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ListShoppingCartTest {

    private final AbstractItem chocolateItem = new Chocolate("1");
    private final AbstractItem appleItem = new Apple("2");
    private final AbstractItem pearItem = new Pear("3");

    @Mock
    private ProductCatalog catalog;

    @InjectMocks
    private ListShoppingCart shoppingCart;

    @Ignore
    @Before
    public void setUp() {
        shoppingCart = new ListShoppingCart(catalog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullShouldThrowIllegalArgumentException() {

        shoppingCart.addItem(null);
    }

    @Test
    public void testAddItemToShoppingCartInvocation() {

        ShoppingCart cart = Mockito.mock(ShoppingCart.class);
        cart.addItem(chocolateItem);
        verify(cart).addItem(chocolateItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullShouldThrowIllegalArgumentException() {
        shoppingCart.removeItem(null);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveShouldThrowItemNotFoundException() {
        shoppingCart.addItem(appleItem);

        shoppingCart.removeItem(chocolateItem);
    }

    @Test
    public void testRemoveItemFromShoppingCartInvocation() {
        ShoppingCart cart = Mockito.mock(ShoppingCart.class);
        cart.addItem(chocolateItem);
        verify(cart).addItem(chocolateItem);

        cart.removeItem(chocolateItem);
        verify(cart).removeItem(chocolateItem);
    }

    @Test
    public void testGetTotalShouldReturnZeroWhenEmpty() {
        assertEquals(0d, this.shoppingCart.getTotal(), 0.0);
    }

    @Test
    public void testGetTotalWithAllItems() {
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(chocolateItem);

        Mockito.when(catalog.getProductInfo("2")).thenReturn(new ProductInfo("apple", "red apple", 1.3d));
        Mockito.when(catalog.getProductInfo("1")).thenReturn(new ProductInfo("chocolate", "nutella", 10.36d));

        double expected = 11.66d; //when chocolate and apple are added
        double actual = shoppingCart.getTotal();

        assertEquals(expected, actual, 0.00d);
    }

    @Test
    public void testGetUniqueItemsShouldReturnEmptyList() {
        Collection<Item> expected = new HashSet<>();

        assertEquals(expected, shoppingCart.getUniqueItems());
    }

    @Test
    public void testGetUniqueItems() {
        shoppingCart.addItem(chocolateItem);
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(appleItem);

        Collection<Item> actualUniqueItems = shoppingCart.getUniqueItems();
        Item[] expected = new Item[] {chocolateItem, appleItem};
        //array of unique items (without additional apple item)

        assertEquals(expected, actualUniqueItems.toArray());
    }

    @Test
    public void testGetSortedItemsShouldReturnOrderedByValue() {
        shoppingCart.addItem(chocolateItem);
        shoppingCart.addItem(chocolateItem);
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(appleItem);
        shoppingCart.addItem(pearItem);

        Object[] expected = new Item[] {appleItem, chocolateItem, pearItem};
        Object[] actual = shoppingCart.getSortedItems().toArray();
        // apples(3) are more than chocolate items(2) so they should be first
        //pears are last and least

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetSortedItemsShouldReturnEmptyList() {
        Object[] actual = shoppingCart.getSortedItems().toArray();

        assertArrayEquals(new Item[]{}, actual);
    }
}
