package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    SharedPreferences sharedPreferences;
    CartAdapter adapter;
    ArrayList<String> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);

        Set<String> cartItems = sharedPreferences.getStringSet("items", new HashSet<>());
        cartList = new ArrayList<>(cartItems);

        adapter = new CartAdapter(this, cartList);
        cartListView.setAdapter(adapter);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(CartActivity.this, "Cart is already empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalAmount = calculateTotal(cartList);

            sharedPreferences.edit().putStringSet("pending_items", new HashSet<>(cartList)).apply();

            Intent intent = new Intent(CartActivity.this, ConfirmationActivity.class);
            intent.putExtra("total_price", totalAmount);
            startActivity(intent);
        });

        Button totalButton = findViewById(R.id.calculateTotalButton);
        totalButton.setOnClickListener(v -> {
            double total = calculateTotal(cartList);
            Toast.makeText(CartActivity.this, "🧾 Total: $" + total, Toast.LENGTH_LONG).show();
        });
    }

    private double calculateTotal(ArrayList<String> cartList) {
        double total = 0;
        for (String item : cartList) {
            try {
                String[] parts = item.split(" × ");
                String[] qtyPricePart = parts[1].split(" - ");
                int quantity = Integer.parseInt(qtyPricePart[0].trim());
                double price = Double.parseDouble(qtyPricePart[1].replace("$", "").trim());
                total += quantity * price;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }
}
