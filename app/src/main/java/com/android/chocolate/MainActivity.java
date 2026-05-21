// MainActivity.java
// This is the entry point of the app.
// It builds the product list in memory (no server needed for this mini-project),
// sets up the RecyclerView, and listens for taps to open the details screen.

package com.android.chocolate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chocolate.adapter.ProductAdapter;
import com.android.chocolate.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    // Intent keys — centralising these avoids typos across activities
    public static final String EXTRA_PRODUCT_ID    = "product_id";
    public static final String EXTRA_PRODUCT_NAME  = "product_name";
    public static final String EXTRA_PRODUCT_DESC  = "product_desc";
    public static final String EXTRA_PRODUCT_PRICE = "product_price";
    public static final String EXTRA_PRODUCT_IMAGE = "product_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_products);

        // GridLayoutManager with 2 columns looks like a real product catalogue
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = buildProductList();

        adapter = new ProductAdapter(this, productList, product -> {
            // When a card is tapped, pass the product data to the details screen via Intent
            Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
            intent.putExtra(EXTRA_PRODUCT_ID,    product.getId());
            intent.putExtra(EXTRA_PRODUCT_NAME,  product.getName());
            intent.putExtra(EXTRA_PRODUCT_DESC,  product.getDescription());
            intent.putExtra(EXTRA_PRODUCT_PRICE, product.getPrice());
            intent.putExtra(EXTRA_PRODUCT_IMAGE, product.getImageResId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    // Product data is hardcoded here.
    // In a real app this would come from a server or local JSON file.
    // Using @drawable/choco_box1 for all items as instructed.
    private List<Product> buildProductList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1, "Classic Dark Box",
                "A luxurious selection of rich dark chocolates, perfect for any occasion.",
                12.99, R.drawable.choco_box1));
        list.add(new Product(2, "Milk Dream Box",
                "Smooth creamy milk chocolates wrapped in a beautiful golden box.",
                10.99, R.drawable.choco_box1));
        list.add(new Product(3, "Mixed Delight Box",
                "A mixed assortment of dark, milk, and white chocolates.",
                14.99, R.drawable.choco_box1));
        list.add(new Product(4, "White Bliss Box",
                "Premium white chocolates with a hint of vanilla bean.",
                11.99, R.drawable.choco_box1));
        list.add(new Product(5, "Truffle Love Box",
                "Handcrafted truffles dusted with cocoa powder and sea salt.",
                17.99, R.drawable.choco_box1));
        list.add(new Product(6, "Hazelnut Heaven Box",
                "Chocolate bonbons filled with toasted hazelnut praline.",
                15.99, R.drawable.choco_box1));
        return list;
    }
}