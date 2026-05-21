// DatabaseHelper.java
// This is the only class that touches the database.
// Centralizing all SQL here means activities never write raw SQL themselves.
// The cart table stores everything needed to rebuild the cart after the app closes.

package com.android.chocolate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.chocolate.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Version bump this number whenever you change the table schema
    private static final String DB_NAME    = "chocolate_gift.db";
    private static final int    DB_VERSION = 1;

    // Table and column constants — avoids hard-coded strings scattered everywhere
    private static final String TABLE_CART      = "cart";
    private static final String COL_ID          = "id";
    private static final String COL_PRODUCT_ID  = "product_id";
    private static final String COL_NAME        = "name";
    private static final String COL_PRICE       = "price";
    private static final String COL_QUANTITY    = "quantity";
    private static final String COL_IMAGE_RES   = "image_res";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the cart table the first time the app runs
        String createTable =
                "CREATE TABLE " + TABLE_CART + " (" +
                        COL_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_PRODUCT_ID + " INTEGER, " +
                        COL_NAME       + " TEXT, " +
                        COL_PRICE      + " REAL, " +
                        COL_QUANTITY   + " INTEGER, " +
                        COL_IMAGE_RES  + " INTEGER" +
                        ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple strategy: drop and recreate on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // --- ADD ITEM ---
    // Inserts a new cart row. Called from ProductDetailsActivity.
    public void addItem(int productId, String name, double price, int quantity, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_ID, productId);
        values.put(COL_NAME,       name);
        values.put(COL_PRICE,      price);
        values.put(COL_QUANTITY,   quantity);
        values.put(COL_IMAGE_RES,  imageResId);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // --- GET ALL ITEMS ---
    // Returns the full cart list for display in CartActivity.
    public List<CartItem> getAllItems() {
        List<CartItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(new CartItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE_RES))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    // --- CLEAR CART ---
    // Called after the user confirms checkout so the cart resets.
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CART);
        db.close();
    }
}