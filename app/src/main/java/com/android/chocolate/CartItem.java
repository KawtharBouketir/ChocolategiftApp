package com.example.chocolategift;

public class CartItem {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int imageResId;

    public CartItem(int id, String name, double price, int quantity, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public double getLineTotal() {
        return price * quantity;
    }
}