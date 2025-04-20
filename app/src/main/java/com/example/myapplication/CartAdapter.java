package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> cartItems;

    public CartAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.cartItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.cartItemName);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        itemName.setText(item);

        deleteButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyDataSetChanged();

            SharedPreferences sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
            Set<String> updatedSet = new HashSet<>(cartItems);
            sharedPreferences.edit().putStringSet("items", updatedSet).apply();

            Toast.makeText(context, "Removed: " + item, Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
