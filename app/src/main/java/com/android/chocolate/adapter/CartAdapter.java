// CartAdapter.java
// Same pattern as ProductAdapter but for cart rows.
// Each row shows the product image, name, quantity, and line total.

package com.android.chocolate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chocolate.R;
import com.android.chocolate.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context   = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.ivImage.setImageResource(item.getImageResId());
        holder.tvName.setText(item.getName());
        holder.tvQty.setText("Qty: " + item.getQuantity());
        holder.tvLineTotal.setText(String.format("$%.2f", item.getLineTotal()));
    }

    @Override
    public int getItemCount() { return cartItems.size(); }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView  tvName, tvQty, tvLineTotal;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage     = itemView.findViewById(R.id.iv_cart_image);
            tvName      = itemView.findViewById(R.id.tv_cart_name);
            tvQty       = itemView.findViewById(R.id.tv_cart_qty);
            tvLineTotal = itemView.findViewById(R.id.tv_cart_line_total);
        }
    }
}