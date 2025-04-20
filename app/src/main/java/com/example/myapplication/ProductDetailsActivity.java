package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productPrice, productQuantity;
    Button addToCartButton, backButton;

    String name, image;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.detailProductImage);
        productName = findViewById(R.id.detailProductName);
        productPrice = findViewById(R.id.detailProductPrice);
        productQuantity = findViewById(R.id.detailProductQuantity);
        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0);
        image = getIntent().getStringExtra("image");

        productName.setText(name);
        productPrice.setText("Price: $" + price);

        SharedPreferences stockPrefs = getSharedPreferences("stock", MODE_PRIVATE);
        int actualQty = stockPrefs.getInt(name, 0);

        if (name.toLowerCase().contains("beef") || name.toLowerCase().contains("chicken") || name.toLowerCase().contains("tomato")) {
            productQuantity.setText("Remaining: " + actualQty + " kg");
        } else {
            productQuantity.setText("Remaining: " + actualQty + " pcs");
        }

        int imageResId = getResources().getIdentifier(image, "drawable", getPackageName());
        productImage.setImageResource(imageResId);

        addToCartButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
            builder.setTitle("Enter quantity to add");

            final EditText input = new EditText(ProductDetailsActivity.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String inputText = input.getText().toString().trim();
                if (!inputText.isEmpty()) {
                    int requestedQty = Integer.parseInt(inputText);

                    if (requestedQty > 0) {
                        SharedPreferences sharedPreferences = getSharedPreferences("cart", Context.MODE_PRIVATE);
                        Set<String> cartItems = sharedPreferences.getStringSet("items", new HashSet<>());
                        cartItems = new HashSet<>(cartItems);

                        cartItems.add(name + " × " + requestedQty + " - $" + price);
                        sharedPreferences.edit().putStringSet("items", cartItems).apply();

                        Toast.makeText(getApplicationContext(), "You selected: " + requestedQty + " " + name, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProductDetailsActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }
}

