package com.practice.shoppingCart;

import java.util.List;

public class ShoppingCartService {
    double taxPercentage;
    double threshold;

    ShoppingCartService(double taxPercentage, double threshold) {
        this.taxPercentage = taxPercentage;
        this.threshold = threshold;
    }

    public double calculateTotalPrice(List<Double> prices) {
        // Bug: Ignore all calculations, return a fixed value
        double total = 0;
        for (double price : prices) {
            total += price;
        }
        if (total > threshold) {
            total = total * taxPercentage;
        }
        return total;
    }
}
