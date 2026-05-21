// ProductAdapter.java
// The adapter is the bridge between the product data list and the RecyclerView UI.
// ViewHolder pattern recycles card views instead of inflating a new one for every item,
// which keeps scrolling smooth even with many products.

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
import com.android.chocolate.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductClickListener listener;

    // Interface lets MainActivity handle the click without the adapter knowing about activities
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener) {
        this.context     = context;
        this.productList = productList;
        this.listener    = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item_product.xml once per visible card slot
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.ivImage.setImageResource(product.getImageResId());

        // Attach click listener to the whole card
        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
    }

    @Override
    public int getItemCount() { return productList.size(); }

    // ViewHolder caches the view references so findViewById is not called repeatedly
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView  tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage  = itemView.findViewById(R.id.iv_product_image);
            tvName   = itemView.findViewById(R.id.tv_product_name);
            tvPrice  = itemView.findViewById(R.id.tv_product_price);
        }
    }
}