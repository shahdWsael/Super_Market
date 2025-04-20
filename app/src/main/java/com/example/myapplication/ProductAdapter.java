package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        Product product = getItem(position);

        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        Button addToCartButton = convertView.findViewById(R.id.addToCartButton);


        productName.setText(product.getName());
        productPrice.setText("Price: $" + product.getPrice());


        SharedPreferences stockPrefs = getContext().getSharedPreferences("stock", Context.MODE_PRIVATE);
        int actualQuantity = stockPrefs.getInt(product.getName(), product.getQuantity());


        String lowerName = product.getName().toLowerCase();
        String unit = (lowerName.contains("beef") || lowerName.contains("chicken") || lowerName.contains("tomato")) ? "kg" : "pcs";

        productQuantity.setText("Remaining: " + actualQuantity + " " + unit);

        // 🟣 Set image
        int imageResId = getContext().getResources().getIdentifier(
                product.getImageName(), "drawable", getContext().getPackageName());
        productImage.setImageResource(imageResId);

        // ✅ Add to cart logic with quantity prompt
        addToCartButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Enter quantity to add");

            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String inputText = input.getText().toString().trim();
                if (!inputText.isEmpty()) {
                    int requestedQty = Integer.parseInt(inputText);

                    if (requestedQty > 0) {
                        SharedPreferences cartPrefs = getContext().getSharedPreferences("cart", Context.MODE_PRIVATE);
                        Set<String> cartItems = cartPrefs.getStringSet("items", new HashSet<>());
                        cartItems = new HashSet<>(cartItems);

                        cartItems.add(product.getName() + " × " + requestedQty + " - $" + product.getPrice());
                        cartPrefs.edit().putStringSet("items", cartItems).apply();

                        Toast.makeText(getContext(), "✅ Added: " + requestedQty + " " + unit + " of " + product.getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        return convertView;
    }
}
