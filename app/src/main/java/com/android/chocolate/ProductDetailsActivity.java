package com.example.chocolategift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtDescription, txtPrice;
    EditText edtQuantity;
    Button btnAddToCart, btnGoToCart;

    int imageResId;
    String productName, productDescription;
    double productPrice;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imgProduct = findViewById(R.id.imgDetailProduct);
        txtName = findViewById(R.id.txtDetailName);
        txtDescription = findViewById(R.id.txtDetailDescription);
        txtPrice = findViewById(R.id.txtDetailPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnGoToCart = findViewById(R.id.btnGoToCart);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        productName = intent.getStringExtra("name");
        productDescription = intent.getStringExtra("description");
        productPrice = intent.getDoubleExtra("price", 0);
        imageResId = intent.getIntExtra("image", R.drawable.choco_box1);

        imgProduct.setImageResource(imageResId);
        txtName.setText(productName);
        txtDescription.setText(productDescription);
        txtPrice.setText(String.format("$ %.2f", productPrice));

        btnAddToCart.setOnClickListener(v -> {
            String qtyText = edtQuantity.getText().toString().trim();

            if (qtyText.isEmpty()) {
                Toast.makeText(this, "Enter quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity;

            try {
                quantity = Integer.parseInt(qtyText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantity <= 0) {
                Toast.makeText(this, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.insertCartItem(productName, productPrice, quantity, imageResId);

            if (result != -1) {
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToCart.setOnClickListener(v ->
                startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class))
        );
    }
}