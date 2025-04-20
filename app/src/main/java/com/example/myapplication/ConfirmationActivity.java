package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    TextView totalText;
    Spinner paymentSpinner;
    Button confirmPaymentButton, backToHomeButton;

    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        totalText = findViewById(R.id.totalText);
        paymentSpinner = findViewById(R.id.paymentSpinner);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        backToHomeButton = findViewById(R.id.backHomeButton);

        total = getIntent().getDoubleExtra("total_price", 0.0);
        totalText.setText("Total: $" + total);

        String[] paymentOptions = {"Please select payment method", "Cash on Delivery", "Visa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paymentOptions);
        paymentSpinner.setAdapter(adapter);

        confirmPaymentButton.setOnClickListener(v -> {
            String selectedMethod = paymentSpinner.getSelectedItem().toString();

            if (selectedMethod.equals("Please select payment method")) {
                Toast.makeText(this, " Please choose a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(ConfirmationActivity.this, SuccessActivity.class);
            intent.putExtra("total_price", total);
            intent.putExtra("payment_method", selectedMethod);
            startActivity(intent);
        });

        backToHomeButton.setOnClickListener(v -> finish());
    }
}
