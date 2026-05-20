package com.example.chocolategift;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private final List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        adapter = new ProductAdapter(productList, product -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
            intent.putExtra("id", product.getId());
            intent.putExtra("name", product.getName());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("image", product.getImageResId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private void loadProducts() {
        productList.clear();

        productList.add(new Product(
                1,
                "Luxury Heart Box",
                "A premium heart-shaped chocolate gift box.",
                19.99,
                R.drawable.choco_box1
        ));

        productList.add(new Product(
                2,
                "Classic Brown Box",
                "Assorted milk, dark, and white chocolates.",
                14.50,
                R.drawable.choco_box1
        ));

        productList.add(new Product(
                3,
                "Golden Surprise Box",
                "Elegant chocolate box for special occasions.",
                24.00,
                R.drawable.choco_box1
        ));
    }
}