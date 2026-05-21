// CartActivity.java
// The final screen. It:
// 1. Loads all cart items from SQLite
// 2. Displays them in a RecyclerView
// 3. Calculates and shows the total price
// 4. Takes the customer name via EditText
// 5. On checkout: validates name, shows order summary, clears the cart

package com.android.chocolate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chocolate.adapter.CartAdapter;
import com.android.chocolate.database.DatabaseHelper;
import com.android.chocolate.model.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private CartAdapter    cartAdapter;
    private List<CartItem> cartItems;

    private RecyclerView rvCart;
    private TextView     tvTotal, tvEmptyCart;
    private EditText     etCustomerName;
    private Button       btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper       = new DatabaseHelper(this);

        rvCart         = findViewById(R.id.rv_cart);
        tvTotal        = findViewById(R.id.tv_total_price);
        tvEmptyCart    = findViewById(R.id.tv_empty_cart);
        etCustomerName = findViewById(R.id.et_customer_name);
        btnCheckout    = findViewById(R.id.btn_checkout);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        loadCart();

        btnCheckout.setOnClickListener(v -> handleCheckout());
    }

    // Reads the cart from SQLite and refreshes the UI
    private void loadCart() {
        cartItems = dbHelper.getAllItems();

        if (cartItems.isEmpty()) {
            tvEmptyCart.setVisibility(android.view.View.VISIBLE);
            rvCart.setVisibility(android.view.View.GONE);
            tvTotal.setText("Total: $0.00");
            return;
        }

        tvEmptyCart.setVisibility(android.view.View.GONE);
        rvCart.setVisibility(android.view.View.VISIBLE);

        cartAdapter = new CartAdapter(this, cartItems);
        rvCart.setAdapter(cartAdapter);

        // Calculate total by summing each item's line total
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getLineTotal();
        }
        tvTotal.setText(String.format("Total: $%.2f", total));
    }

    private void handleCheckout() {
        String customerName = etCustomerName.getText().toString().trim();

        // Validate that the user entered their name before proceeding
        if (customerName.isEmpty()) {
            etCustomerName.setError("Please enter your name to continue");
            etCustomerName.requestFocus();
            return;
        }

        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build a readable order summary for the confirmation dialog
        StringBuilder summary = new StringBuilder();
        summary.append("Customer: ").append(customerName).append("\n\n");
        summary.append("Order Summary:\n");
        summary.append("──────────────────\n");

        double total = 0;
        for (CartItem item : cartItems) {
            summary.append("• ").append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append("  →  $").append(String.format("%.2f", item.getLineTotal()))
                    .append("\n");
            total += item.getLineTotal();
        }

        summary.append("──────────────────\n");
        summary.append("Total: $").append(String.format("%.2f", total));
        summary.append("\n\nThank you for your order! 🍫");

        // Show the confirmation dialog before clearing the cart
        new AlertDialog.Builder(this)
                .setTitle("Order Confirmed")
                .setMessage(summary.toString())
                .setPositiveButton("Done", (dialog, which) -> {
                    dbHelper.clearCart();
                    finish(); // Go back to the product list
                })
                .setCancelable(false)
                .show();
    }
}