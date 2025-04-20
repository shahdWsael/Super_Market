package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SuccessActivity extends AppCompatActivity {

    TextView successTitle, totalTextFinal, paymentMethodText, itemDetails;
    Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        successTitle = findViewById(R.id.successTitle);
        totalTextFinal = findViewById(R.id.totalTextFinal);
        paymentMethodText = findViewById(R.id.paymentMethodText);
        itemDetails = findViewById(R.id.itemDetails);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        double total = getIntent().getDoubleExtra("total_price", 0.0);
        String method = getIntent().getStringExtra("payment_method");

        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        Set<String> cartItems = sharedPreferences.getStringSet("items", new HashSet<>());
        ArrayList<String> cartList = new ArrayList<>(cartItems);

        totalTextFinal.setText("Total: $" + total);
        paymentMethodText.setText("Payment Method: " + method);

        StringBuilder detailsBuilder = new StringBuilder();
        for (String item : cartList) {
            detailsBuilder.append("\u2022 ").append(item).append("\n");
        }
        itemDetails.setText(detailsBuilder.toString());

        SharedPreferences stockPrefs = getSharedPreferences("stock", MODE_PRIVATE);
        SharedPreferences.Editor stockEditor = stockPrefs.edit();
        for (String item : cartList) {
            try {
                String[] parts = item.split(" × ");
                String name = parts[0].trim();
                int qty = Integer.parseInt(parts[1].split(" - ")[0].trim());
                int current = stockPrefs.getInt(name, 999);
                stockEditor.putInt(name, Math.max(current - qty, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stockEditor.apply();
        sharedPreferences.edit().remove("items").apply();

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
