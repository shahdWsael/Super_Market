package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    EditText searchInput;
    RadioGroup searchTypeGroup;
    CheckBox availableCheckBox;
    Switch saleSwitch;
    Button searchButton, backButton;

    SharedPreferences stockPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = findViewById(R.id.searchInput);
        searchTypeGroup = findViewById(R.id.searchTypeGroup);
        availableCheckBox = findViewById(R.id.availableCheckBox);
        saleSwitch = findViewById(R.id.saleSwitch);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        stockPrefs = getSharedPreferences("stock", MODE_PRIVATE);


        saleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SearchActivity.this, " No discounted items available at the moment.", Toast.LENGTH_SHORT).show();
                saleSwitch.setChecked(false);
            }
        });


        searchButton.setOnClickListener(v -> {
            String keyword = searchInput.getText().toString().trim().toLowerCase();
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Please enter a search keyword", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = searchTypeGroup.getCheckedRadioButtonId();
            boolean byName = selectedId == R.id.radioName;
            boolean onlyAvailable = availableCheckBox.isChecked();

            ArrayList<Product> matched = new ArrayList<>();
            ArrayList<Product> allProducts = ProductData.getAllProducts(this);

            for (Product product : allProducts) {
                int stock = stockPrefs.getInt(product.getName(), product.getQuantity());

                boolean match = false;
                if (byName && product.getName().toLowerCase().contains(keyword)) {
                    match = true;
                } else if (!byName) {
                    try {
                        double targetPrice = Double.parseDouble(keyword);
                        if (product.getPrice() == targetPrice) {
                            match = true;
                        }
                    } catch (NumberFormatException ignored) {}
                }

                if (match && (!onlyAvailable || stock > 0)) {
                    matched.add(product);
                }
            }

            if (matched.isEmpty()) {
                Toast.makeText(this, "No matching products found ", Toast.LENGTH_LONG).show();
            } else {
                Product p = matched.get(0);
                Intent intent = new Intent(this, ProductDetailsActivity.class);
                intent.putExtra("name", p.getName());
                intent.putExtra("price", p.getPrice());
                intent.putExtra("image", p.getImageName());
                intent.putExtra("quantity", stockPrefs.getInt(p.getName(), p.getQuantity()));
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
