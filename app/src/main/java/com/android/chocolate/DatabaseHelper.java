package com.example.chocolategift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chocolate_gift.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CART = "cart_table";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "product_name";
    public static final String COL_PRICE = "product_price";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_IMAGE = "image_res_id";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CART + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT, " +
                    COL_PRICE + " REAL, " +
                    COL_QUANTITY + " INTEGER, " +
                    COL_IMAGE + " INTEGER" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public long insertCartItem(String name, double price, int quantity, int imageResId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PRICE, price);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_IMAGE, imageResId);

        return db.insert(TABLE_CART, null, values);
    }

    public ArrayList<CartItem> getAllCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE));

                cartItems.add(new CartItem(id, name, price, quantity, image));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COL_PRICE + " * " + COL_QUANTITY + ") AS total FROM " + TABLE_CART,
                null
        );

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getDouble(0);
        }

        cursor.close();
        return total;
    }

    public void clearCart() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CART, null, null);
    }
}