// CartItem.java
// A cart item is NOT the same as a product.
// It holds a product reference plus the quantity the user chose.
// Keeping this separate makes total-price calculation clean and simple.

package com.android.chocolate.model;

public class CartItem {

    private int id;         // SQLite row id
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private int imageResId;

    public CartItem(int id, int productId, String name, double price, int quantity, int imageResId) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public int getId()          { return id; }
    public int getProductId()   { return productId; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public int getQuantity()    { return quantity; }
    public int getImageResId()  { return imageResId; }

    // Convenience method used in CartActivity to compute the line total
    public double getLineTotal() { return price * quantity; }
}