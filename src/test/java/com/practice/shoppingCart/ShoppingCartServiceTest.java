package com.practice.shoppingCart;


import com.practice.shoppingCart.ShoppingCartService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ShoppingCartServiceTest {

    @Test
    public void testCalculateTotalPrice_ExceedsThreshold() {
        // Tax is 10%, threshold is 50
        ShoppingCartService service = new ShoppingCartService(1.10, 50.0);
        // Prices sum to 60, which exceeds the threshold
        double total = service.calculateTotalPrice(Arrays.asList(10.0, 20.0, 30.0));
        // We expect total to be 66 (60 * 1.10)
        assertEquals(66.0, total, 0.01);
    }

    @Test
    public void testCalculateTotalPrice_BelowThreshold() {
        // Tax is 10%, threshold is 50
        ShoppingCartService service = new ShoppingCartService(1.10, 50.0);
        // Prices sum to 30, which does not exceed the threshold
        double total = service.calculateTotalPrice(Arrays.asList(10.0, 10.0, 10.0));
        // We expect total to be 30 (no tax applied)
        assertEquals(30.0, total, 0.01);
    }
}
