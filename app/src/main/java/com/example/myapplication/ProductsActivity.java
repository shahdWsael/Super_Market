package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    ListView productListView;
    TextView categoryTitle;
    Button viewCartButton;
    Button openSearchButton;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        categoryTitle = findViewById(R.id.categoryTitle);
        productListView = findViewById(R.id.productListView);
        viewCartButton = findViewById(R.id.viewCartButton);
        openSearchButton = findViewById(R.id.openSearchButton);
        backButton = findViewById(R.id.backButton);


        backButton.setOnClickListener(v -> finish());

        String category = getIntent().getStringExtra("category");
        categoryTitle.setText("Products in: " + category);

        List<Product> productList = new ArrayList<>();

        if ("Vegetables".equals(category)) {
            productList.add(new Product("Tomato", "tomato", 1.5, 10));
            productList.add(new Product("Cucumber", "cucumber", 1.2, 8));
            productList.add(new Product("Carrot", "carrot", 1.0, 6));
            productList.add(new Product("Lettuce", "lettuce", 2.0, 5));
            productList.add(new Product("Onion", "onion", 0.8, 7));
        } else if ("Meat".equals(category)) {
            productList.add(new Product("Beef", "beef", 12.0, 6));
            productList.add(new Product("Chicken", "chicken", 7.0, 8));
            productList.add(new Product("Chicken Wings", "chicken_wings", 5.5, 10));
            productList.add(new Product("Chicken Breast", "chicken_breast", 6.5, 9));
            productList.add(new Product("Beef Steak", "beef_steak", 14.0, 5));
        } else if ("Snacks".equals(category)) {
            productList.add(new Product("Chips", "chips", 2.0, 10));
            productList.add(new Product("Chocolate", "chocolate", 3.0, 7));
            productList.add(new Product("Cookies", "cookies", 2.5, 12));
            productList.add(new Product("Gummy Bears", "gummy", 1.5, 6));
            productList.add(new Product("Granola Bar", "granola", 2.2, 8));
        } else if ("Cleaning Supplies".equals(category)) {
            productList.add(new Product("Soap", "soap", 1.5, 12));
            productList.add(new Product("Detergent", "detergent", 4.0, 6));
            productList.add(new Product("Bleach", "bleach", 3.5, 4));
            productList.add(new Product("Glass Cleaner", "glass_cleaner", 2.8, 5));
            productList.add(new Product("Floor Cleaner", "floor_cleaner", 5.0, 3));
        } else {
            productList.add(new Product("Sample Item", "ic_launcher_foreground", 0.0, 0));
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        productListView.setAdapter(adapter);


        SharedPreferences stockPrefs = getSharedPreferences("stock", MODE_PRIVATE);
        SharedPreferences.Editor stockEditor = stockPrefs.edit();
        for (Product product : productList) {
            String key = product.getName();
            if (!stockPrefs.contains(key)) {
                stockEditor.putInt(key, product.getQuantity());
            }
        }
        stockEditor.apply();

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
            startActivity(intent);
        });

        openSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}
