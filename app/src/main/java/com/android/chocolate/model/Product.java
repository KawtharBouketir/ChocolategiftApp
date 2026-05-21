// Product.java
// This class represents a single chocolate gift box product.
// Using a model class keeps data organized and separates it from UI logic.
// Each field maps to what you display on the product list and details screen.

package com.android.chocolate.model;

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private int imageResId; // drawable resource ID (e.g. R.drawable.choco_box1)

    public Product(int id, String name, String description, double price, int imageResId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters — the adapter and activities read data through these
    public int getId()             { return id; }
    public String getName()        { return name; }
    public String getDescription() { return description; }
    public double getPrice()       { return price; }
    public int getImageResId()     { return imageResId; }
}