package com.company.restaurant;

import com.company.restaurant.customer.VipCustomer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMJTDiningPlace {
    private final static int CHEFS_COUNT = 3;
    private final Order BASIC_ORDER = new Order(Meal.MUSAKA, new VipCustomer(diningPlace));
    private final Order OTHER_BASIC_ORDER = new Order(Meal.SPAGHETTI, new VipCustomer(diningPlace));

    @Mock
    private MJTDiningPlace mockedDiningPlace;

    private static MJTDiningPlace diningPlace;

    @BeforeClass
    public static void setUp() throws Exception {
        diningPlace = new MJTDiningPlace(CHEFS_COUNT);
    }

    @Test
    public void testDiningPlaceReturnsCount() {
        final int expected = 10;
        when(mockedDiningPlace.getOrdersCount()).thenReturn(expected);

        Assert.assertEquals(expected, mockedDiningPlace.getOrdersCount());
    }

    @Test
    public void testGetChefsReturnsCount() {
        final int expected = 10;
        when(mockedDiningPlace.getChefs()).thenReturn(new Chef[expected]);

        Assert.assertArrayEquals(new Chef[expected], mockedDiningPlace.getChefs());
    }

    @Test
    public void testSubmitMethodAddsOrder() {
        diningPlace.submitOrder(BASIC_ORDER);

        Assert.assertEquals(1, diningPlace.getOrdersCount());
    }

    @Test
    public void testGetChefReturnsEmptyList() {
        diningPlace = new MJTDiningPlace(0);
        final Chef[] expected = new Chef[0];
        Assert.assertArrayEquals(expected, diningPlace.getChefs());
    }

    @Test
    public void testChefIdGetsIncremented() {
        //we have 3 chefs by default
        final Chef[] chefs = diningPlace.getChefs();

        final String firstChefIdExpected = "Chef 0";
        final String lastChefIdExpected = "Chef 2";

        Assert.assertEquals(firstChefIdExpected, chefs[0].getName());
        Assert.assertEquals(lastChefIdExpected, chefs[chefs.length - 1].getName());
    }

    @Test
    public void testNextOrderReturnsOrder() {
        when(mockedDiningPlace.nextOrder()).thenReturn(BASIC_ORDER);

        Assert.assertEquals(BASIC_ORDER, mockedDiningPlace.nextOrder());
        Assert.assertNotEquals(OTHER_BASIC_ORDER, mockedDiningPlace.nextOrder());
    }


}
