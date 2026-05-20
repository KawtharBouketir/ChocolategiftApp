package com.example.chocolategift;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerCart;
    TextView txtTotal;
    EditText edtCustomerName;
    Button btnCheckout, btnRefreshCart;

    DatabaseHelper dbHelper;
    ArrayList<CartItem> cartList;
    CartAdapter adapter;
    double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        txtTotal = findViewById(R.id.txtTotal);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnRefreshCart = findViewById(R.id.btnRefreshCart);

        dbHelper = new DatabaseHelper(this);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        loadCart();

        btnRefreshCart.setOnClickListener(v -> loadCart());

        btnCheckout.setOnClickListener(v -> {
            String customerName = edtCustomerName.getText().toString().trim();

            if (customerName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Order Confirmed")
                    .setMessage("Thank you " + customerName + "!\nTotal: $ " + String.format("%.2f", totalPrice))
                    .setPositiveButton("OK", (dialog, which) -> {
                        dbHelper.clearCart();
                        loadCart();
                        edtCustomerName.setText("");
                    })
                    .show();
        });
    }

    private void loadCart() {
        cartList = dbHelper.getAllCartItems();
        adapter = new CartAdapter(cartList);
        recyclerCart.setAdapter(adapter);

        totalPrice = dbHelper.getTotalPrice();
        txtTotal.setText("Total: $ " + String.format("%.2f", totalPrice));
    }
}