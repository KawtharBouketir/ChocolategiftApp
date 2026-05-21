// ProductDetailsActivity.java
// Receives the product data from MainActivity via Intent.
// The user picks a quantity and taps "Add to Cart", which saves to SQLite.
// Then they can navigate to the cart from the floating cart button.

package com.android.chocolate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.chocolate.database.DatabaseHelper;

public class ProductDetailsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private ImageView ivProduct;
    private TextView  tvName, tvDesc, tvPrice, tvQty;
    private Button    btnDecrease, btnIncrease, btnAddToCart, btnViewCart;

    private int    quantity   = 1;   // default quantity
    private int    productId;
    private String productName;
    private String productDesc;
    private double productPrice;
    private int    productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        dbHelper = new DatabaseHelper(this);

        // --- Read data passed from MainActivity ---
        productId    = getIntent().getIntExtra(MainActivity.EXTRA_PRODUCT_ID, 0);
        productName  = getIntent().getStringExtra(MainActivity.EXTRA_PRODUCT_NAME);
        productDesc  = getIntent().getStringExtra(MainActivity.EXTRA_PRODUCT_DESC);
        productPrice = getIntent().getDoubleExtra(MainActivity.EXTRA_PRODUCT_PRICE, 0.0);
        productImage = getIntent().getIntExtra(MainActivity.EXTRA_PRODUCT_IMAGE, R.drawable.choco_box1);

        // --- Bind views ---
        ivProduct    = findViewById(R.id.iv_detail_image);
        tvName       = findViewById(R.id.tv_detail_name);
        tvDesc       = findViewById(R.id.tv_detail_desc);
        tvPrice      = findViewById(R.id.tv_detail_price);
        tvQty        = findViewById(R.id.tv_quantity);
        btnDecrease  = findViewById(R.id.btn_decrease);
        btnIncrease  = findViewById(R.id.btn_increase);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnViewCart  = findViewById(R.id.btn_view_cart);

        // --- Populate UI ---
        ivProduct.setImageResource(productImage);
        tvName.setText(productName);
        tvDesc.setText(productDesc);
        tvPrice.setText(String.format("$%.2f", productPrice));
        tvQty.setText(String.valueOf(quantity));

        // --- Quantity controls ---
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQty.setText(String.valueOf(quantity));
            }
        });

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            tvQty.setText(String.valueOf(quantity));
        });

        // --- Add to Cart ---
        // Saves the item to SQLite and shows a confirmation toast
        btnAddToCart.setOnClickListener(v -> {
            dbHelper.addItem(productId, productName, productPrice, quantity, productImage);
            Toast.makeText(this, productName + " added to cart!", Toast.LENGTH_SHORT).show();
        });

        // --- Go to Cart ---
        btnViewCart.setOnClickListener(v -> {
            startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
        });
    }
}