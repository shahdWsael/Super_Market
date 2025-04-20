package com.example.myapplication;

import android.content.Context;

import java.util.ArrayList;

public class ProductData {

    public static ArrayList<Product> getAllProducts(Context context) {
        ArrayList<Product> products = new ArrayList<>();

        // Vegetables
        products.add(new Product("Tomato", "tomato", 1.5, 10));
        products.add(new Product("Cucumber", "cucumber", 1.2, 8));
        products.add(new Product("Carrot", "carrot", 1.0, 6));
        products.add(new Product("Lettuce", "lettuce", 2.0, 5));
        products.add(new Product("Onion", "onion", 0.8, 7));

        // Meat
        products.add(new Product("Beef", "beef", 12.0, 6));
        products.add(new Product("Chicken", "chicken", 7.0, 8));
        products.add(new Product("Chicken Wings", "chicken_wings", 5.5, 10));
        products.add(new Product("Chicken Breast", "chicken_breast", 6.5, 9));
        products.add(new Product("Beef Steak", "beef_steak", 14.0, 5));

        // Snacks
        products.add(new Product("Chips", "chips", 2.0, 10));
        products.add(new Product("Chocolate", "chocolate", 3.0, 7));
        products.add(new Product("Cookies", "cookies", 2.5, 12));
        products.add(new Product("Gummy Bears", "gummy", 1.5, 6));
        products.add(new Product("Granola Bar", "granola", 2.2, 8));

        // Cleaning
        products.add(new Product("Soap", "soap", 1.5, 12));
        products.add(new Product("Detergent", "detergent", 4.0, 6));
        products.add(new Product("Bleach", "bleach", 3.5, 4));
        products.add(new Product("Glass Cleaner", "glass_cleaner", 2.8, 5));
        products.add(new Product("Floor Cleaner", "floor_cleaner", 5.0, 3));

        return products;
    }
}
